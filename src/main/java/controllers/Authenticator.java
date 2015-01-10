package controllers;

import models.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Bob on 21/11/2014.
 */
public class Authenticator {



    public String test(){
        return "PROUUUUUUUUUT !!!!!!!!!";
    }

    public boolean check(HttpServletRequest req) throws Exception {
        String email = req.getParameter("email");
        String timestamp = req.getParameter("timestamp");
        String signature = req.getParameter("signature");
        javax.servlet.http.Cookie[] cookies = req.getCookies();

        try {
            signature = URLDecoder.decode(signature, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String salt = "";
        String calculatedSignature = null;
        String encryptedPassword = null;
        String httpUrl = null;
        String res = "";
        String password = null;
        Response.ResponseBuilder rb = null;

        Utils utils = new Utils();
        Cookie cooky = utils.getCookies(cookies);

        MessageDigest sha1 = MessageDigest.getInstance("SHA1");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(email);

        //Si l'utilisateur demandé n'existe pas, on le jette
        if (user == null) {
            return false;
        } else {
            password = user.getPassword();
            try {

                salt = utils.calculateHash(sha1, email);
                String httpVerb = "GET";
                String url = req.getRequestURI()+"?email=" + email + "&timestamp=" + timestamp;
                httpUrl = httpVerb + ":" + url;

                calculatedSignature = Utils.calcShaHash(httpUrl, password);

                if(signature.toString().equals(calculatedSignature.toString())){
                    return true;
                } else {
                    return false;
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }


    public boolean isAdmin() {
        return false;
    }
}
