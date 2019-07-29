package com.myitzar.cbe.ejb;

import com.myitzar.cbe.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Created by Gabby on 2017/04/16.
 */

@WebService
@Stateless
public class MyITZARBackendServiceBean {
    Logger logger = LoggerFactory.getLogger(MyITZARBackendServiceBean.class);

    @PersistenceContext(unitName = "MyITZAR")
    EntityManager em;

    @WebMethod
    public String loginUser(@WebParam(name = "username") String username,
                            @WebParam(name = "password") String password){
        String query = "from " + User.class.getName() + " u where u.username=:username AND u.password=:password";
        User user;
        try {
            user = (User) em.createQuery(query).setParameter("username",username)
                    .setParameter("password", password).getSingleResult();
        } catch (NoResultException nre){
            user = null;
        }

        if (user == null){
            logger.info("User " + username + " login attempt failed at: " + new Date());
            return "failed";
        }
        logger.info("User " + username + " logged in at: " + new Date());
        return "success";
    }

    @WebMethod
    public String registerUser(@WebParam(name = "newUsername") String newUsername,
                               @WebParam(name = "newPassword") String newPassword){
        User user = new User();
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        em.persist(user);
        logger.info("User " + newUsername + " new user registered at: " + new Date());
        return "success";
    }

    @WebMethod
    public String updateUserPassword(@WebParam (name = "username") String username,
                                     @WebParam (name = "newPassword") String newPassword){
        String query = "from " + User.class.getName() + " u where u.username=:username";
        User user = (User) em.createQuery(query).setParameter("username", username).getSingleResult();
        user.setPassword(newPassword);
        em.persist(user);
        logger.info("User " + username + " updated at: " + new Date());
        return "success";
    }
}
