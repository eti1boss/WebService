package controllers;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Bob on 23/10/2014.
 */
@Stateless
@MultipartConfig(location = "/", fileSizeThreshold=1024*1024*10,    // 10 MB
        maxFileSize=1024*1024*50,          // 50 MB
        maxRequestSize=1024*1024*100)      // 100 MB
public class HelloServlet extends HttpServlet {

    SecurityContext securityContext;
    private static final String UPLOAD_DIR = "/uploads/";

    @Override
    @Produces("application/json")
    protected void doGet(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();
        String redirect = req.getRequestURL().toString();

        resp.getWriter().println(action+"<br/>"+redirect);

        if(action.equals("/hello")) {
            req.getRequestDispatcher("/WEB-INF/login.html").forward(req, resp);
        }

        if (action.equals(" login")) {
            boolean access = false;

            Authenticator auth = new Authenticator();
            Utils utils = new Utils();
            boolean hasCookie = false;

            Cookie[] listCook = req.getCookies();
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
                    access = auth.check(req);
                    if (access) {
                        if (auth.isAdmin()) {
                            redirect = "admin";
                        } else {
                            redirect = "user/list";
                        }
                        String email = req.getParameter("email");
                        String token = utils.getToken(email);
                        //NewCookie newCook = new NewCookie("token", token, "", "", "commentaire", 5, true, true);
                        Cookie newCookie = new Cookie("token", token);
                        newCookie.setHttpOnly(true);
                        newCookie.setMaxAge(5);
                        newCookie.setSecure(false);
                        newCookie.setComment("Taste my new cookie !");
                        resp.addCookie(newCookie);

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

            resp.getWriter().write(redirect);
        }

        if (action == "pictures") {

            /*Picture picture = new Picture();
            List<String> images = picture.getPictures();
            redirect = "\"[";
            for (int i = 0; i < images.size(); i++) {
                String pic = images.get(i).replace("\\","/");
                redirect += "\\\"" + pic +"\\\"";
                if(i < images.size()-1){redirect += ",";}
            }*/
            redirect += "]\"";
        }

        if (action == "logout") {

        }





        //Point d'entrée du login
        //Si c'est ok
        //Je vais sur la page du user : liste + upload
        //Si je suis admin
        //Je vais sur la console d'admin
        //Si c'est KO
        //Je me casse


//        redirect = "{" + "\"lol\":\"ccccc\"" + "}";


//        resp.getWriter().write(req.getServletPath());
        //resp.sendRedirect("login.html");
//        resp.getWriter().println(redirect);
       //resp.getWriter().println("BEFORE");
/*
        File file = new File("/pictures/25_y3b.png");
        resp.setContentType("image/png");
        resp.setContentLength((int) file.length());
        FileInputStream in = new FileInputStream(file);
        OutputStream out = resp.getOutputStream();
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();
*/
//        resp.getWriter().println("AFTER");



//        resp.sendRedirect("index.html");
    }

    @Override
    protected void doPost(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException {

        // gets absolute path of the web application
        String applicationPath = getServletContext().getRealPath("");

        String user = "";
        Cookie[] listCook = request.getCookies();
        if (listCook != null) {
            for(int i = 0;i<listCook.length;i++){
                if(listCook[i].getName().equals("token")){
                    user = listCook[i].getValue().substring(0, listCook[i].getValue().indexOf(":"));
                }
            }
        }

        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR+user;


        // creates the save directory if it does not exists
        File fileSaveDir = new File(UPLOAD_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getPath());

        String fileName = null;
        String azer = null;
        //Get all the parts from request and write it to the file on server
        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            //part.write(fileName);

            InputStream inputStream = part.getInputStream();

            BufferedImage originalImage = ImageIO.read(inputStream);
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            int height= originalImage.getHeight();
            int width = originalImage.getWidth();
            Math.floor(width*0.75);

            BufferedImage resizeImageJpg3 = resizeImage(originalImage, type, (int)(width * 0.75), (int)(height * 0.75));
            BufferedImage resizeImageJpg2 = resizeImage(originalImage, type, (int)(width * 0.5), (int)(height * 0.5));
            //BufferedImage resizeImageJpg1 = resizeImage(originalImage, type, (int)(width * 0.25), (int)(height * 0.25));

            File directorySave = new File(fileSaveDir+"/"+fileName);
            if (!directorySave.exists()) {
                directorySave.mkdirs();
            }

            ImageIO.write(originalImage, "jpg", new File(directorySave+"/"+"100_"+fileName));
            ImageIO.write(resizeImageJpg3, "jpg", new File(directorySave+"/"+"75_"+fileName));
            ImageIO.write(resizeImageJpg2, "jpg", new File(directorySave+"/"+"50_"+fileName));
            //ImageIO.write(resizeImageJpg1, "jpg", new File(directorySave+"/"+"25_"+fileName));
        }
        Authenticator auth = new Authenticator();
        response.getWriter().write(request.isSecure()+"\n"+user+"\njob done");
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

}
