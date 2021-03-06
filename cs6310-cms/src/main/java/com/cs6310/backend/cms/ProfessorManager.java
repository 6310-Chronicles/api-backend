package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.helpers.Utils;
import com.cs6310.backend.model.AccessCredential;
import com.cs6310.backend.model.Course;
import com.cs6310.backend.model.PersonDetails;
import com.cs6310.backend.model.Professor;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 11/3/15.
 */
public class ProfessorManager {


    private EntityManager entityManager;
    private Logger logger;

    public ProfessorManager() {
        entityManager = DatabaseUtil.getEntityManager();
        logger = Logger.getLogger(StudentManager.class);

    }

    /**
     * Add a new prof and save to database
     *
     * @param available
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


    public String addProf(String profId, String available, String firstName, String lastName, String profilePic,
                          String mobilePhone, String email, String gender, String address, String username, String password, String secretQuestion,
                          String secretAnswer, String active) {


        Professor professor = new Professor(profId);
        professor.setProfId(profId);
        professor.setAvailable(Utils.convertStringToBool(available));

        PersonDetails personDetails = new PersonDetails();
        personDetails.setFirstName(firstName);
        personDetails.setLastName(lastName);
        personDetails.setMobilePhone(mobilePhone);
        personDetails.setEmail(email);
        personDetails.setGender(gender);
        personDetails.setAddress(address);
        personDetails.setProfilePic(profilePic);

        personDetails.setUuid(String.valueOf(UUID.randomUUID()));

        professor.setPersonDetails(personDetails);
        professor.setUuid(String.valueOf(UUID.randomUUID()));

        AccessCredential accessCredential = new AccessCredential();
        accessCredential.setUsername(username);
        accessCredential.setPassword(password);
        accessCredential.setSecretQuestion(secretQuestion);
        accessCredential.setSecretAnswer(secretAnswer);
        accessCredential.setActive(Utils.convertStringToBool(active));

        professor.setAccessCredential(accessCredential);


        try {
            entityManager.getTransaction().begin();
            entityManager.persist(professor);
            entityManager.getTransaction().commit();
            return null;
        } catch (RollbackException e) {
            e.printStackTrace();

            String code = DatabaseUtil.getSqlErrorCode(e);

            return DatabaseUtil.getCauseMessage(e);
        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        }

    }

    public void close() {
        entityManager.close();
    }



    /**
     * Update adminstrator
     *
     * @param uuid
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

    public String updateProf(String uuid, String availability, String firstName, String lastName, String profilePic,
                             String mobilePhone, String email, String gender, String address, String username, String password, String secretQuestion,
                             String secretAnswer, String active) {

        Professor professor = null;

        entityManager.getTransaction().begin();
        try {

            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Professor.getByUUID");
            query.setParameter("uuid", uuid);
            professor = (Professor) query.getSingleResult();

            professor.setAvailable(Utils.convertStringToBool(availability));


            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error -" + e.getMessage());
        }


        if (professor != null) {

            PersonDetails personDetails = professor.getPersonDetails();

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


            professor.setPersonDetails(personDetails);


            AccessCredential accessCredential = professor.getAccessCredential();

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

            professor.setAccessCredential(accessCredential);

            try {
                entityManager.getTransaction().begin();
                entityManager.merge(professor);
                entityManager.getTransaction().commit();

                return "OK";
            } catch (RollbackException e) {
                e.printStackTrace();

                String code = DatabaseUtil.getSqlErrorCode(e);

                logger.info("Error updating Professor: '{}'" + code);

                return DatabaseUtil.getCauseMessage(e);
            } catch (Exception e) {
                e.printStackTrace();

                return DatabaseUtil.getCauseMessage(e);
            }

        } else
            return null;

    }


    /**
     * Assign competency Course to a prof
     *
     * @param profUUID
     * @param courseUUID
     * @return
     */
    public String addProfCompetentCourse(String profUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Professor.getByUUID");
            query.setParameter("uuid", profUUID);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            professor.getCompetentCourseList().add(course);

            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }


    /**
     * remove competent Course from a prof
     *
     * @param profUUID
     * @param courseUUID
     * @return
     */
    public String removeProfCompetentCourse(String profUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Professor.getByUUID");
            query.setParameter("uuid", profUUID);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            List<Course> courseList = professor.getCompetentCourseList();

            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    professor.getCompetentCourseList().remove(t);
            }
//            professor.setCompetentCourseList(courseList);
            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }



    /**
     * Assign teaching Course to a prof
     *
     * @param profUUID
     * @param courseUUID
     * @return
     */
    public String addProfTeachingCourse(String profUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Professor.getByUUID");
            query.setParameter("uuid", profUUID);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            professor.getTeachingCourseList().add(course);

            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }



    /**
     * remove teaching Course from a prof
     *
     * @param profUUID
     * @param courseUUID
     * @return
     */

    public String removeProfTeachingCourse(String profUUID, String courseUUID) {
        try {

            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Professor.getByUUID");
            query.setParameter("uuid", profUUID);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            List<Course> courseList = professor.getTeachingCourseList();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    professor.getTeachingCourseList().remove(t);
            }
            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }


    /**
     * Get All persisted Professors
     *
     * @return
     */
    public List<Professor> getAllProfessors() {
        entityManager.getTransaction().begin();
        Query query = entityManager
                .createNamedQuery("com.cs6310.backend.model.Professor.getAll");

        List list = query.getResultList();
        entityManager.getTransaction().commit();
        if (list != null) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * Get Professor by id
     *
     * @param uuid
     * @return
     */
    public Professor getProfessor(String uuid) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Professor.getByUUID");
            query.setParameter("uuid", uuid);

            Professor professor = (Professor) query.getSingleResult();

            entityManager.getTransaction().commit();
            return professor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Delete Professor by UUID
     *
     * @param id
     * @return
     */
    public String deleteProfessorByUUID(String id) {
        try {

            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Professor.getByUUID");
            query.setParameter("uuid", id);
            Professor professor = (Professor) query.getSingleResult();

            entityManager.remove(professor);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }


    public String addProfCompetentCourseCSV(String profId, String courseId) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Professor.getByProfId");
            query.setParameter("profId", profId);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getCourseID");
            querycourse.setParameter("courseId", courseId);
            Course course = (Course) querycourse.getSingleResult();

            professor.getCompetentCourseList().add(course);

            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }

    public String addProfTeachingCourseCSV(String profId, String courseId) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Professor.getByProfId");
            query.setParameter("profId", profId);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getCourseID");
            querycourse.setParameter("courseId", courseId);
            Course course = (Course) querycourse.getSingleResult();

            professor.getTeachingCourseList().add(course);

            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }
    }



}
