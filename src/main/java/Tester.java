import controllers.Authenticator;
import controllers.UserDAO;
import controllers.Utils;
import org.apache.commons.io.FileUtils;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.ws.rs.core.Context;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bob on 08/01/2015.
 */
@Stateless
@MultipartConfig(location = "/", fileSizeThreshold=1024*1024*10,    // 10 MB
        maxFileSize=1024*1024*50,          // 50 MB
        maxRequestSize=1024*1024*100)      // 100 MB
@WebServlet(name = "Tester")
public class Tester extends HttpServlet {

    private static final String UPLOAD_DIR = "/uploads/";

    @Override
    protected void doPost(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException {

        String action = request.getPathInfo();

        if (action.equals("/register")) {
//            response.sendRedirect("");
            String resultat = "début";
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UserDAO userDAO = new UserDAO();
            resultat = userDAO.addUser(email,password);
            response.getWriter().println(resultat);

        }

        if (action.equals("/upload")) {

            // gets absolute path of the web application
            String applicationPath = getServletContext().getRealPath("");
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            String user = "public/"+stamp.getTime();

            Cookie[] listCook = request.getCookies();
            boolean hasCookie = false;
            if (listCook != null) {
                for(int i = 0;i<listCook.length;i++){
                    if(listCook[i].getName().equals("token") || listCook[i].getName().equals("public")){
                        user = listCook[i].getValue().substring(0, listCook[i].getValue().indexOf(":"));
                        hasCookie = true;
                    }
                }
            }
            if(!hasCookie) {
                Cookie newCookie = new Cookie("public", user+":");
                newCookie.setHttpOnly(true);
                newCookie.setSecure(false);
                response.addCookie(newCookie);
            }


            // constructs path of the directory to save uploaded file
            String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR+user;


            // creates the save directory if it does not exists
            File fileSaveDir = new File(UPLOAD_DIR+user);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            System.out.println("Upload File Directory="+fileSaveDir.getPath());

            String fileName = null;
            //Get all the parts from request and write it to the file on server
            for (Part part : request.getParts()) {
                stamp = new Timestamp(System.currentTimeMillis());
                fileName = getFileName(part);
                InputStream inputStream = part.getInputStream();

                BufferedImage originalImage = ImageIO.read(inputStream);
                int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                int height= originalImage.getHeight();
                int width = originalImage.getWidth();

                BufferedImage resizeImageJpg3 = resizeImage(originalImage, type, (int)(width * 0.75), (int)(height * 0.75));
                BufferedImage resizeImageJpg2 = resizeImage(originalImage, type, (int)(width * 0.5), (int)(height * 0.5));
                //BufferedImage resizeImageJpg1 = resizeImage(originalImage, type, (int)(width * 0.25), (int)(height * 0.25));

                File directorySave = new File(fileSaveDir+"/"+stamp.getTime()+"_"+fileName);
                if (!directorySave.exists()) {
                    directorySave.mkdirs();
                }

                ImageIO.write(originalImage, "jpg", new File(directorySave+"/"+"100_"+fileName));
                ImageIO.write(resizeImageJpg3, "jpg", new File(directorySave+"/"+"075_"+fileName));
                ImageIO.write(resizeImageJpg2, "jpg", new File(directorySave+"/"+"050_"+fileName));
                //ImageIO.write(resizeImageJpg1, "jpg", new File(directorySave+"/"+"025_"+fileName));
            }
            response.getWriter().write(request.isSecure() + "\n" + user + "\njob done");

        }
    }
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height){
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        String redirect = "DEBUT";

        if(action.equals("/url")) {
            Timestamp stamp = (new Timestamp(System.currentTimeMillis()));
            long time = stamp.getTime();
            Date date = new Date(time);
            response.getWriter().println(time+"<br/>"+date);
        }

        if(action.equals("/welcome") || action.equals("/")) {
            request.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(request, response);
        }

        if(action.equals("/delete")) {
            String picName = request.getParameter("pictureName");
            FileUtils.deleteDirectory(new File("/uploads/e.bossuet@gmail.com/"+picName));
            response.sendRedirect("pictures");
        }

        if( action.equals("/pictures")) {
            boolean hasCookie = false;
            Cookie[] listCook = request.getCookies();
            String user = "";
            boolean isPublic = true;
            if (listCook != null) {
                for(int i = 0;i<listCook.length;i++){
                    if(listCook[i].getName().equals("token")) {
                        user = listCook[i].getValue().substring(0, listCook[i].getValue().indexOf(":"));
                        hasCookie = true;
                        isPublic = false;
                    }
                    if(listCook[i].getName().equals("public")) {
                        user = listCook[i].getValue().substring(0, listCook[i].getValue().indexOf(":"));
                        hasCookie = true;
                    }
                }
            }
            if(hasCookie){

                File directory = new File("/uploads/"+user);
                HashMap<String,List<String>> files = new HashMap<>();
                if(directory.exists()){
                    File[] fList = directory.listFiles();
                    for (File file : fList) {
                        String dir = file.getName();
                        List<String> pic = new ArrayList<>();
                        for(File file2 : file.listFiles()){
                            pic.add(file2.getPath());
                        }
                        files.put(dir,pic);
                    }

                    request.setAttribute("user",isPublic ? "" : user);
                    request.setAttribute("file",files);
                    request.getRequestDispatcher("/WEB-INF/jsp/lol.jsp").forward(request,response);
                } else {
                    response.getWriter().println("You don't have any pictures");
                }

            } else {
                response.sendRedirect("upload");
            }
        }
        if (action.equals("/upload")) {
            Cookie[] listCook = request.getCookies();
            String user = "";
            String isPublic = "";
            if (listCook != null) {
                for(int i = 0;i<listCook.length;i++){
                    if(listCook[i].getName().equals("token")) {
                        user = listCook[i].getValue().substring(0, listCook[i].getValue().indexOf(":"));
                    }
                    if(listCook[i].getName().equals("public")) {
                        user = "";
                        isPublic = "isPublic";
                    }
                }
            }
            request.setAttribute("isPublic",isPublic);
            request.setAttribute("user",user);
            request.getRequestDispatcher("/WEB-INF/jsp/upload.jsp").forward(request,response);
        }
        if( action.equals("/logout")) {
            Cookie newCookie = new Cookie("token", null);
            newCookie.setHttpOnly(true);
            newCookie.setMaxAge(0);
            newCookie.setSecure(false);
            response.addCookie(newCookie);
            response.sendRedirect("upload");
        }
        if( action.equals("/login")) {
            boolean access = false;

            Authenticator auth = new Authenticator();
            Utils utils = new Utils();
            boolean hasCookie = false;

            Cookie[] listCook = request.getCookies();
            if (listCook != null) {
                for(int i = 0;i<listCook.length;i++){
                    if(listCook[i].getName().equals("token")){
                        hasCookie = true;
                    }
                }
            }
            if (!hasCookie) {
                redirect = "listCook == null";
                try {
                    access = auth.check(request);
                    if (access) {
                        if (auth.isAdmin()) {
                            redirect = "admin";
                        } else {
                            redirect = "user";
                        }
                        String email = request.getParameter("email");
                        String token = utils.getToken(email);
                        //NewCookie newCook = new NewCookie("token", token, "", "", "commentaire", 5, true, true);
                        Cookie newCookie = new Cookie("token", token);
                        newCookie.setHttpOnly(true);
//                        newCookie.setMaxAge(30);
                        newCookie.setSecure(false);
                        response.addCookie(newCookie);

                    } else {
                        redirect = "error";
                    }
                } catch (Exception e) {
                    redirect = e.getMessage();
                    e.printStackTrace();
                }
            } else {
                redirect = "BOUGE PAS COCO, ON VA VERIFIER TON COOKIE, ET TENTE PAS DE NOUS ENTUBER !!!! BATARD VA !!!";
            }
            response.sendRedirect("pictures");
        }

    }
}
