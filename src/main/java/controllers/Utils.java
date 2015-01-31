package controllers;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by Bob on 18/12/2014.
 */
public class Utils {

    private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static String mySalt = "toto";

    public static String calcShaHash(String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String result = null;

        try {
            Key signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = Base64.encodeBase64String(rawHmac);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String calculateHash(MessageDigest algorithm,
                                 String message) throws Exception{
        MessageDigest algorithm1 = algorithm;
        String message1 = message;

        algorithm.update(message.getBytes());

        byte[] hash = algorithm.digest();

        return byteArray2Hex(hash);
    }

    private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public Cookie getCookies(Cookie[] cookies){
        //On récupère la liste des cookies, et on sélectionne celui qui contient le token
        javax.servlet.http.Cookie cooky = null;
        Integer nbCookies = 0;
        if (cookies != null) {
            nbCookies = cookies.length;
            for (javax.servlet.http.Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    cooky=cookie;
                }
            }
        }
        return cooky;
    }

    public String getToken(String email) throws Exception {

        String timeStamp = String.valueOf(new Date().getTime());
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        String signature = calculateHash(sha1,email+mySalt+timeStamp);
        return email+":"+timeStamp+":"+signature;
    }

    public boolean checkToken(String token) throws Exception {
        String[] data = token.split(":");
        String email = data[0];
        String timeStamp = data[1];
        String signature = data[2];
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        String calculatedSignature = calculateHash(sha1,email+mySalt+timeStamp);

        if(signature.equals(calculatedSignature)){
            return true;
        } else {
            return false;
        }
    }
}
