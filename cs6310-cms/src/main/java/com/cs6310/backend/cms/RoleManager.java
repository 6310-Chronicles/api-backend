package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.Privilege;
import com.cs6310.backend.model.Role;
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
    public String addRole(String name) {

        entityManager.getTransaction().begin();
        try {

            Role role = new Role();
            role.setName(name);
            role.setUuid(String.valueOf(UUID.randomUUID()));


            entityManager.persist(role);
            entityManager.getTransaction().commit();

            return "";

        } catch (Exception e) {

            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);

        }
    }


    /**
     * Add privilege to an existing role
     *
     * @param roleUUID
     * @param privilegeUUID
     * @return
     */
    public String addPrivilegeToRole(String roleUUID, String privilegeUUID) {

        entityManager.getTransaction().begin();
        try {

            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Role.getByUUID");
            query.setParameter("uuid", roleUUID);
            Role role = (Role) query.getSingleResult();


            Query queryPrivilege = entityManager.createNamedQuery("com.cs6310.backend.model.Privilege.getByUUID");
            queryPrivilege.setParameter("uuid", privilegeUUID);
            Privilege privilege = (Privilege) queryPrivilege.getSingleResult();


            role.getPrivileges().add(privilege);

            entityManager.merge(role);

            entityManager.getTransaction().commit();


            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        } finally {
            entityManager.close();
        }
    }


    /**
     * remove privilege to an existing role
     *
     * @param roleUUID
     * @param privilegeUUID
     * @return
     */
    public String removePrivilegeFromRole(String roleUUID, String privilegeUUID) {

        entityManager.getTransaction().begin();
        try {

            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Role.getByUUID");
            query.setParameter("uuid", roleUUID);
            Role role = (Role) query.getSingleResult();


            Query queryPrivilege = entityManager.createNamedQuery("com.cs6310.backend.model.Privilege.getByUUID");
            queryPrivilege.setParameter("uuid", privilegeUUID);
            Privilege privilege = (Privilege) queryPrivilege.getSingleResult();

            List<Privilege> privilegeList = role.getPrivileges();
            int size = privilegeList.size();
            for (int t = 0; t < size; t++) {
                Privilege privilege1 = privilegeList.get(t);
                if (privilege1.getUuid().equalsIgnoreCase(privilege.getUuid()))
                    role.getPrivileges().remove(t);
            }

            // role.setPrivileges(privilegeList);

            entityManager.merge(role);
            entityManager.getTransaction().commit();

            return "OK";

        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        } finally {
            entityManager.close();
        }
    }


    /**
     * Get All persisted roles
     *
     * @return
     */
    public List getAllRoles() {
        entityManager.getTransaction().begin();
        Query query = entityManager
                .createNamedQuery("com.cs6310.backend.model.Role.getAll");
        List roleList = query.getResultList();

        entityManager.getTransaction().commit();
        if (roleList != null) {
            return roleList;
        } else {
            return null;
        }
    }


    public Role getRole(String uuid) {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Role.getByUUID");
            query.setParameter("uuid", uuid);

            if (query.getSingleResult() != null) {
                Role role = (Role) query.getSingleResult();

                role.setPrivileges(role.getPrivileges());

                return role;
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


        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }


    /**
     * Delete a Role by passing a uuid
     *
     * @return
     */

    public String deleteRole(String uuid) {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Role.getByUUID");
            query.setParameter("uuid", uuid);

            if (query.getSingleResult() != null) {

                Role role = (Role) query.getSingleResult();
                entityManager.remove(role);
                entityManager.getTransaction().commit();
                return "OK";
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        } finally {
            entityManager.close();
        }


    }


}
