package controllers;

import models.User;
import services.UserService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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

/**
 * Created by Bob on 23/10/2014.
 */
@Path("/users")
@MultipartConfig(location="/")
public class UserController {
    @Inject
    private UserService userService;

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
