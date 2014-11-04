package services;

import models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.List;
/**
 * Created by Bob on 23/10/2014.
 */
@Stateless
@Path("/user-service")
public class UserService {
    @PersistenceContext
    private EntityManager em;

    public List<User> getAll(){
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        return query.getResultList();
    }


    @GET
    @Path("/users")
    public Response getUserById(
            @Context Request req,
            @QueryParam("email") String email,
            @QueryParam("id") String id
    )
    {
        TypedQuery<User> query = em.createQuery("select u from User u where u.email = '"+email+"'", User.class);
        Response.ResponseBuilder rb = Response.ok(query.getSingleResult());
        return rb.build();
    }

    @PUT
    @Path("/users/{email}")
    public Response updateUserById(@PathParam("email") String email)
    {
        TypedQuery<User> query = em.createQuery("update User u set u.email = 'hehe@lol.com' where u.email = '"+email+"'", User.class);
        return Response.status(200).build();
    }

}
