package controllers;

/**
 * Created by Bob on 24/12/2014.
 */

import models.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.transaction.*;
import javax.transaction.RollbackException;
import java.security.MessageDigest;

public class UserDAO {

    public String addUser(String email, String password){
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
//        if (this.getUser(email) != null) {
            MessageDigest sha1 = null;
            try {
                sha1 = MessageDigest.getInstance("SHA1");
                Utils utils = new Utils();
                password = utils.calculateHash(sha1,utils.calculateHash(sha1,password)+utils.calculateHash(sha1,email));

                User a = new User();
                em.persist(a);
                a.setEmail(email);
                a.setPassword(password);
                a.setAdmin(false);

		redirect = String.valueOf(em.createNativeQuery("SELECT * from users").getResultList().size());
            } catch (Exception e) {
		redirect = e.getMessage();
                e.printStackTrace();
            }

        em.joinTransaction();
        try {
            transaction.commit();
            redirect = email + " created !";
        } catch (RollbackException e) {
            redirect = "RollbackException <br/>"+e.getMessage();
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            redirect = "HeuristicMixedException <br/>"+e.getMessage();
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            redirect = "HeuristicRollbackException <br/>"+e.getMessage();
            e.printStackTrace();
        } catch (SystemException e) {
            redirect = "SystemException <br/>"+e.getMessage();
            e.printStackTrace();
        }

        return redirect;
    }

    public User getUser(String email){
        String redirect = "debut";
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("customerManager");
        EntityManager em = emf.createEntityManager();


        TypedQuery<User> res = em.createQuery("select u from User u where u.email = '"+email+"'", User.class);
	User user = null;
        try {
            user = res.getSingleResult();
        } catch (NoResultException e) {
		user = null;
        }

        em.joinTransaction();

        return user;
    }
}
