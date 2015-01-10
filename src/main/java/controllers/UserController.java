package controllers;

import models.User;
import services.UserService;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.List;

/**
 * Created by Bob on 23/10/2014.
 */
@Path("/users")
@MultipartConfig(location="/")
public class UserController{

    private UserService userService;

    public String merde(){
        userService = new UserService();
        return userService.chiotte();
    }

    public List<User> getUser(String email){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("customerManager");
        EntityManager em = factory.createEntityManager();
        TypedQuery<User> query = em.createQuery("select u from User u where u.email = '" + email + "'", User.class);
        return query.getResultList();
    }

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll() {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (User user : userService.getAll()) {
            builder.add(Json.createObjectBuilder().add("email", user.getEmail()));
        }

        return builder.build();
    }

    @Path("/file")
    @POST
    @Produces("application/json")
    public String doUpload(@Context HttpServletRequest request) throws IOException, ServletException {
        JsonArrayBuilder array = Json.createArrayBuilder();

        for (Part part : request.getParts()) {
            String name = null;
            long size = 0;
            try {
                name = part.getSubmittedFileName();
                size = part.getSize();

//                array.add(addFile(name, size, "anId"));
                part.delete();

            } catch (Exception e) {
            }
        }

        JsonObject ret = Json.createObjectBuilder().add("files", array).build();
        return "toto";

    }

}
