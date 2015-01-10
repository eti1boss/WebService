package controllers;

import com.sun.org.apache.xml.internal.security.utils.Base64;

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
    private static String message;
    private static MessageDigest algorithm;
    private static String mySalt = "toto";

    public static String calcShaHash(String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String result = null;

        try {
            Key signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = Base64.encode(rawHmac);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String calculateHash(MessageDigest algorithm,
                                 String message) throws Exception{
        this.algorithm = algorithm;
        this.message = message;

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
}
