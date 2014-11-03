package controllers;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Bob on 23/10/2014.
 */
@Stateless
@WebServlet(name = "hello", urlPatterns = "/up")
@MultipartConfig(location = "/", fileSizeThreshold=1024*1024*10,    // 10 MB
        maxFileSize=1024*1024*50,          // 50 MB
        maxRequestSize=1024*1024*100)      // 100 MB
public class HelloServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // gets absolute path of the web application
        String applicationPath = getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(UPLOAD_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getPath());

        String fileName = null;
        String azer = null;
        //Get all the parts from request and write it to the file on server
        for (Part part : req.getParts()) {
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
            BufferedImage resizeImageJpg1 = resizeImage(originalImage, type, (int)(width * 0.25), (int)(height * 0.25));

            File directorySave = new File(fileSaveDir+"/"+fileName);
            if (!directorySave.exists()) {
                directorySave.mkdirs();
            }

            ImageIO.write(originalImage, "jpg", new File(directorySave+"/"+"100_"+fileName));
            ImageIO.write(resizeImageJpg3, "jpg", new File(directorySave+"/"+"75_"+fileName));
            ImageIO.write(resizeImageJpg2, "jpg", new File(directorySave+"/"+"50_"+fileName));
            ImageIO.write(resizeImageJpg1, "jpg", new File(directorySave+"/"+"25_"+fileName));

        }

        resp.getWriter().write("job done");
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
