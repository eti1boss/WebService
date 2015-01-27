package controllers;

/**
 * Created by Bob on 24/12/2014.
 */

import models.Path;
import org.apache.commons.lang.RandomStringUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.transaction.*;
import javax.transaction.RollbackException;

public class PathDAO {

    public String addPicture(String path){
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
        Path newPath = new Path();
        em.persist(newPath);
        newPath.setPath(path);
        newPath.setUrl(RandomStringUtils.random(8, true, true));
        em.joinTransaction();

        try {
            transaction.commit();
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

    public Path getURL(String path){
        String redirect = "debut";
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("customerManager");
        EntityManager em = emf.createEntityManager();

        UserTransaction transaction = null;

        try {
            transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }

        TypedQuery<Path> res = em.createQuery("select p from Path p where p.path = '"+path+"'", Path.class);
        Path thePath;
        try {
            thePath = res.getSingleResult();
        } catch (NoResultException e) {
            thePath = null;
        }
        em.joinTransaction();
        try {
            transaction.commit();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }

        return thePath;
    }
}