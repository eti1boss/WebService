package services;

import models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @Path("/users/{email}")
    public Response getUserById(@PathParam("email") String email, @Context Request req)
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
