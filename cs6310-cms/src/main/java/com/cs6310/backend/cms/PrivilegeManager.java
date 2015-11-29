package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.Privilege;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 11/3/15.
 */
public class PrivilegeManager {

    private final Logger logger;
    private EntityManager entityManager;

    public PrivilegeManager() {
        entityManager = DatabaseUtil.getEntityManager();
        logger = Logger.getLogger(AdministratorManager.class);
    }

    /**
     * @param name
     * @return
     */
    public String addPrivilege(String name) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        entityManager.getTransaction().begin();
        try {

            Privilege privilege = new Privilege();
            privilege.setName(name);
            privilege.setUuid(String.valueOf(UUID.randomUUID()));

            entityManager.persist(privilege);
            entityManager.getTransaction().commit();


            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }


    /**
     * Delete a privilege by passing a uuid
     *
     * @return
     */

    public String deletePrivilege(String uuid) {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Privilege.getByUUID");
            query.setParameter("uuid", uuid);

            if (query.getSingleResult() != null) {

                Privilege privilege = (Privilege) query.getSingleResult();

                entityManager.remove(privilege);
                entityManager.getTransaction().commit();
                return "OK";
            } else {
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        }


    }

    public String deleteAllPrivileges() {
        boolean resp = false;
        entityManager.getTransaction().begin();
        try {
            int deletedCount = entityManager.createQuery("DELETE FROM com.cs6310.backend.model.Privilege").executeUpdate();

            entityManager.flush();
            entityManager.getTransaction().commit();
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        }

    }


    public Privilege getPrivilege(String uuid) {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Privilege.getByUUID");
            query.setParameter("uuid", uuid);

            if (query.getSingleResult() != null) {
                return (Privilege) query.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Get All persisted users
     *
     * @return
     */
    public List getAllPrivileges() {
        entityManager.getTransaction().begin();
        Query query = entityManager
                .createNamedQuery("com.cs6310.backend.model.Privilege.getAll");
        entityManager.getTransaction().commit();

        List list = query.getResultList();

        if (list != null) {
            return list;
        } else {
            return null;
        }
    }


}
