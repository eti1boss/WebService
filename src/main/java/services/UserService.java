package services;

import org.apache.commons.codec.binary.Base64;
import controllers.Authenticator;
import models.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

/**
 * Created by Bob on 23/10/2014.
 */
@Stateless
@Path("/user-service")
public class UserService{

    private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static String message;

    @PersistenceContext(unitName = "customerManager") public EntityManager em;
    @PersistenceUnit(name = "customerManager") public EntityManagerFactory factory;

    public String chiotte(){

        factory = Persistence.createEntityManagerFactory("customerManager");
        em = factory.createEntityManager();
        TypedQuery<User> res = em.createQuery("select u.email from User u ", User.class);
        String access = res.getResultList().toString();
        return access;
    }

    public List<User> getAll(){
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    @GET
    @Path("/users")
    @Produces("application/json")
    public Response getUserById(
                                @Context HttpServletRequest request,
                                @QueryParam("email") String email,
                                @QueryParam("timestamp") String timestamp,
                                @QueryParam("signature") String signature
                                ) throws Exception {
        try {
            signature = URLDecoder.decode(signature,"UTF-8");
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
        TypedQuery<User> query = em.createQuery("select u from User u where u.email = '" + email + "'", User.class);

        //On récupère la liste des cookies, et on sélectionne celui qui contient le token
        javax.servlet.http.Cookie[] cookies = request.getCookies();
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
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");

        //Si l'utilisateur demandé n'existe pas, on le jette
        if (query.getResultList().isEmpty()) {
            rb = Response.ok("Access denied :(");
        } else {
            password = query.getSingleResult().getPassword();

            try {
                salt = calculateHash(sha1, email);
                encryptedPassword = calculateHash(sha1, calculateHash(sha1, password) + salt);
                String httpVerb = "GET";
                String url = request.getRequestURL()+"?email=" + email + "&timestamp=" + timestamp;
                httpUrl = httpVerb + ":" + url;

                calculatedSignature = calcShaHash(httpUrl,password);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            rb = Response.ok(res);

            boolean addCookie = true;
            boolean secure = false;
            int expires = 0;
            String cookieName = "NULL";
            String cookieValue = "NULL";
            if (cooky != null) {
                expires = cooky.getMaxAge();
                secure = cooky.getSecure();
                addCookie = false;
                cookieName = cooky.getName();
                cookieValue = cooky.getValue();
            }

            /*
            final Random r = new SecureRandom();
            byte[] mySalt = new byte[64];
            r.nextBytes(mySalt);
            String encodedSalt = Base64.encode(mySalt);
            */

            String timeStamp = String.valueOf(new Date().getTime());
            String mySalt = "toto";
            String token = calculateHash(sha1,email+ mySalt +timeStamp)+":"+timeStamp;

            Authenticator auth = new Authenticator();
            String toto = auth.test();

            if(signature.toString().equals(calculatedSignature.toString())){
                NewCookie newCookie = new NewCookie("token", token, "", "", "commentaire", 5    , false,true);
                res = "{" +
                        "\"res\":\"Access granted :)\"," +
                        "\"token\":\""+token+"\","+
                        "\"cookie name\":\""+cookieName+"\","+
                        "\"cookie value\":\""+cookieValue+"\","+
                        "\"nbCookies\":\""+nbCookies+"\","+
                        "\"expires\":\""+expires+"\","+
                        "\"secure\":\""+secure+"\""+
                        //"\"toto\":"+toto +
                        "}";
                rb = Response.ok(res);
                if(addCookie){
                    rb.cookie(newCookie);
                }
            } else {
                res = signature+"\nAccess denied :(";
                rb = Response.ok(res);
            }
        }

        return rb.build();
    }

    @PUT
    @Path("/users/{email}")
    public Response updateUserById(@PathParam("email") String email)
    {
        TypedQuery<User> query = em.createQuery("update User u set u.email = 'hehe@lol.com' where u.email = '"+email+"'", User.class);
        return Response.status(200).build();
    }

    public static String calcShaHash (String data, String key) {
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

    public static String calculateHash(MessageDigest algorithm,
                                       String message) throws Exception{
        MessageDigest algorithm1 = algorithm;
        UserService.message = message;

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

}
