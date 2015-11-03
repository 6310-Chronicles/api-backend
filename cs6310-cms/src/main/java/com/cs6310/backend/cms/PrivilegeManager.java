package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.Privilege;
import com.cs6310.backend.model.Role;
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
    public boolean addPrivilege(String name) {
        boolean resp = false;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        entityManager.getTransaction().begin();
        try {

            Privilege privilege = new Privilege();
            privilege.setName(name);
            privilege.setUuid(String.valueOf(UUID.randomUUID()));

            entityManager.persist(privilege);
            entityManager.getTransaction().commit();
            resp = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }


    /**
     * Get all persisted roles
     *
     * @return
     */
    public List getAllPrivileges() {
        entityManager.getTransaction().begin();

        Query query = entityManager
                .createNamedQuery("Privilege.getAll");

        entityManager.getTransaction().commit();
        if (query.getResultList() == null) {
            return null;
        } else {
            return query.getResultList();
        }
    }


    public Role getPrivilege(String uuid) {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("Privilege.getByUUID");
            query.setParameter("uuid", uuid);

            if (query.getSingleResult() != null) {
                return (Role) query.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean deleteAllPrivileges() {
        boolean resp = false;
        entityManager.getTransaction().begin();
        try {
            int deletedCount = entityManager.createQuery("DELETE FROM com.cs6310.backend.model.Privilege").executeUpdate();

            System.out.println("@NO of Deleted recored: " + deletedCount);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }


}
