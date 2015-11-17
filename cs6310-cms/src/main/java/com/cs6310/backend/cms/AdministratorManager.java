package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.helpers.Utils;
import com.cs6310.backend.model.AccessCredential;
import com.cs6310.backend.model.Administrator;
import com.cs6310.backend.model.PersonDetails;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.UUID;

public class AdministratorManager {

    private final Logger logger;
    private EntityManager entityManager;

    public AdministratorManager() {
        entityManager = DatabaseUtil.getEntityManager();
        logger = Logger.getLogger(AdministratorManager.class);
    }


    /**
     * Add a new admin and save to database
     *
     * @param administratorId
     * @param firstName
     * @param lastName
     * @param profilePic
     * @param mobilePhone
     * @param email
     * @param gender
     * @param address
     * @param username
     * @param password
     * @param secretQuestion
     * @param secretAnswer
     * @param active
     * @return
     */

    public String addAdministrator(String administratorId, String firstName, String lastName, String profilePic,
                                   String mobilePhone, String email, String gender, String address, String username, String password, String secretQuestion,
                                   String secretAnswer, String active) {


        Administrator administrator = new Administrator();

        administrator.setAdministratorId(administratorId);

        administrator.setUuid(String.valueOf(UUID.randomUUID()));

        PersonDetails personDetails = new PersonDetails();
        personDetails.setFirstName(firstName);
        personDetails.setLastName(lastName);
        personDetails.setMobilePhone(mobilePhone);
        personDetails.setEmail(email);
        personDetails.setGender(gender);
        personDetails.setAddress(address);
        personDetails.setProfilePic(profilePic);

        personDetails.setUuid(String.valueOf(UUID.randomUUID()));

        administrator.setPersonDetails(personDetails);


        AccessCredential accessCredential = new AccessCredential();
        accessCredential.setUsername(username);
        accessCredential.setPassword(password);
        accessCredential.setSecretQuestion(secretQuestion);
        accessCredential.setSecretAnswer(secretAnswer);
        accessCredential.setActive(Utils.convertStringToBool(active));

        administrator.setAccessCredential(accessCredential);


        try {
            entityManager.getTransaction().begin();
            entityManager.persist(administrator);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (RollbackException e) {
            e.printStackTrace();

            String code = DatabaseUtil.getSqlErrorCode(e);

            logger.info("Error creating administrator: '{}'" + code);

            return DatabaseUtil.getCauseMessage(e);

        } catch (Exception e) {
            e.printStackTrace();

            String code = DatabaseUtil.getSqlErrorCode(e);

            logger.info("Error creating administrator: '{}'" + code);
            return DatabaseUtil.getCauseMessage(e);
        }

    }


    /**
     * Update adminstrator
     *
     * @param administratorId
     * @param firstName
     * @param lastName
     * @param profilePic
     * @param mobilePhone
     * @param email
     * @param gender
     * @param address
     * @param username
     * @param password
     * @param secretQuestion
     * @param secretAnswer
     * @param active
     * @return
     */

    public String updateAdministrator(String uuid, String administratorId, String firstName, String lastName, String profilePic,
                                      String mobilePhone, String email, String gender, String address, String username, String password, String secretQuestion,
                                      String secretAnswer, String active) {

        Administrator administrator = null;

        entityManager.getTransaction().begin();
        try {

            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Administrator.getByUUID");

            query.setParameter("uuid", uuid);

            administrator = (Administrator) query.getSingleResult();

            entityManager.getTransaction().commit();

        } catch (Exception e) {
            logger.error("Error -" + e.getMessage());
        }


        if (administrator != null) {

            PersonDetails personDetails = administrator.getPersonDetails();

            if (firstName != null)
                personDetails.setFirstName(firstName);
            if (lastName != null)
                personDetails.setLastName(lastName);
            if (mobilePhone != null)
                personDetails.setMobilePhone(mobilePhone);
            if (email != null)
                personDetails.setEmail(email);
            if (gender != null)
                personDetails.setGender(gender);
            if (address != null)
                personDetails.setAddress(address);
            if (profilePic != null)
                personDetails.setProfilePic(profilePic);


            administrator.setPersonDetails(personDetails);


            AccessCredential accessCredential = administrator.getAccessCredential();

            if (username != null)
                accessCredential.setUsername(username);
            if (password != null)
                accessCredential.setPassword(password);
            if (secretQuestion != null)
                accessCredential.setSecretQuestion(secretQuestion);
            if (secretAnswer != null)
                accessCredential.setSecretAnswer(secretAnswer);
            if (active != null)
                accessCredential.setActive(Utils.convertStringToBool(active));


            administrator.setAccessCredential(accessCredential);


            try {
                entityManager.getTransaction().begin();
                entityManager.merge(administrator);
                entityManager.getTransaction().commit();
                return "OK";

            } catch (RollbackException e) {
                e.printStackTrace();

                String code = DatabaseUtil.getSqlErrorCode(e);

                logger.info("Error updating administrator: '{}'" + code);

                return DatabaseUtil.getCauseMessage(e);
            } catch (Exception e) {
                e.printStackTrace();

                return DatabaseUtil.getCauseMessage(e);
            }

        } else return null;

    }


    public void close() {
        entityManager.close();
    }




    /**
     * Get All persisted Administrators
     *
     * @return
     */
    public List getAllAdminstrators() {
        entityManager.getTransaction().begin();
        Query query = entityManager
                .createNamedQuery("com.cs6310.backend.model.Administrator.getAll");


        List list = query.getResultList();

        entityManager.getTransaction().commit();

        if (list != null) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * Get Administrator by id
     *
     * @param uuid
     * @return
     */
    public Administrator getAdministrator(String uuid) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Administrator.getByUUID");
            query.setParameter("uuid", uuid);
            entityManager.getTransaction().commit();

            return (Administrator) query.getSingleResult();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Delete Administrator by UUID
     *
     * @param id
     * @return
     */
    public String deleteAdministratorByUUID(String id) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Administrator.getByUUID");
            query.setParameter("uuid", id);

            entityManager.remove(query.getSingleResult());
            entityManager.getTransaction().commit();
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }


}
