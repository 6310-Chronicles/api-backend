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
public class RoleManager {

    private final Logger logger;
    private EntityManager entityManager;

    public RoleManager() {
        entityManager = DatabaseUtil.getEntityManager();
        logger = Logger.getLogger(AdministratorManager.class);
    }

    /**
     * Add a new role
     *
     * @param name
     * @return
     */
    public boolean addRole(String name) {
        boolean resp = false;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        entityManager.getTransaction().begin();
        try {

            Role role = new Role();
            role.setName(name);
            role.setUuid(String.valueOf(UUID.randomUUID()));


            entityManager.persist(role);
            entityManager.getTransaction().commit();
            resp = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }


    /**
     * Add privilege to an existing role
     *
     * @param roleUUID
     * @param privilegeUUID
     * @return
     */
    public boolean addPrivilegeToRole(String roleUUID, String privilegeUUID) {
        boolean resp = false;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        entityManager.getTransaction().begin();
        try {

            Query query = entityManager.createNamedQuery("Role.getByUUID");
            query.setParameter("uuid", roleUUID);
            Role role = (Role) query.getSingleResult();


            Query queryPrivilege = entityManager.createNamedQuery("Privilege.getByUUID");
            queryPrivilege.setParameter("uuid", privilegeUUID);
            Privilege privilege = (Privilege) queryPrivilege.getSingleResult();

            role.getPrivileges().add(privilege);


            entityManager.merge(role);
            entityManager.getTransaction().commit();
            resp = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }


    /**
     * remove privilege to an existing role
     *
     * @param roleUUID
     * @param privilegeUUID
     * @return
     */
    public boolean removePrivilegeFromRole(String roleUUID, String privilegeUUID) {
        boolean resp = false;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        entityManager.getTransaction().begin();
        try {

            Query query = entityManager.createNamedQuery("Role.getByUUID");
            query.setParameter("uuid", roleUUID);
            Role role = (Role) query.getSingleResult();


            Query queryPrivilege = entityManager.createNamedQuery("Privilege.getByUUID");
            queryPrivilege.setParameter("uuid", privilegeUUID);
            Privilege privilege = (Privilege) queryPrivilege.getSingleResult();

            List<Privilege> privilegeList = role.getPrivileges();
            int size = privilegeList.size();
            for (int t = 0; t < size; t++) {
                Privilege privilege1 = privilegeList.get(t);
                if (privilege1.getUuid().equalsIgnoreCase(privilege.getUuid()))
                    privilegeList.remove(t);
            }

            role.setPrivileges(privilegeList);

            entityManager.merge(role);
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
    public List getAllRoles() {
        entityManager.getTransaction().begin();

        Query query = entityManager
                .createNamedQuery("Role.getAll");

        entityManager.getTransaction().commit();
        if (query.getResultList() == null) {
            return null;
        } else {
            return query.getResultList();
        }
    }


    public Role getRole(String uuid) {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("Role.getByUUID");
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


    public boolean deleteAllRoles() {
        boolean resp = false;
        entityManager.getTransaction().begin();
        try {
            int deletedCount = entityManager.createQuery("DELETE FROM Role").executeUpdate();
            System.out.println("@NO of Deleted recored: " + deletedCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }


}
