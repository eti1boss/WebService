package controllers;

/**
 * Created by Bob on 24/12/2014.
 */

import models.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.*;

public class UserDAO {
    public User getUser(String email) {
        String redirect = "debut";
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("customerManager");
        EntityManager em = emf.createEntityManager();

        UserTransaction transaction = null;
        try {
            transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        } catch (NamingException e) {
            redirect = e.getMessage();
            e.printStackTrace();
        }
        try {
            transaction.begin();
        } catch (NotSupportedException e) {
            redirect = e.getMessage();
            e.printStackTrace();
        } catch (SystemException e) {
            redirect = e.getMessage();
            e.printStackTrace();
        }

        TypedQuery<User> res = em.createQuery("select u from User u where u.email = 'e.bossuet@gmail.com'", User.class);
        User user = res.getSingleResult();
/*
        User a = new User();
        em.persist(a);
        a.setEmail(String.valueOf(new Date().getTime()));
        a.setPassword("YOUPIIIIIIIIIIII");

        User newU = em.createQuery("select u from User u where u.id = 2",User.class).getSingleResult();
        newU.setEmail("ok");*/

        em.joinTransaction();
        try {
            transaction.commit();
        } catch (RollbackException e) {
            redirect = e.getMessage();
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            redirect = e.getMessage();
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            redirect = e.getMessage();
            e.printStackTrace();
        } catch (SystemException e) {
            redirect = e.getMessage();
            e.printStackTrace();
        }
        return user;
    }
}