package controllers;

import models.User;
import services.UserService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
/**
 * Created by Bob on 23/10/2014.
 */
@Path("/users")
public class UserController {
    @Inject
    private UserService userService;

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll(){
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (User user : userService.getAll()) {
            builder.add(Json.createObjectBuilder().add("email", user.getEmail()));
        }
        return builder.build();
    }

    @Path("/test")
    @GET
    @Produces("application/json")
    public String allez(){
        return "NOM DE DIEU DE PUTAIN DE BORDEL DE MERDE DE CONNARD DE SALOPERIE D'ENCULE DE TA MERE";
    }
}
