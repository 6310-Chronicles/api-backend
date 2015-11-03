package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.*;
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
     * @param profId
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


    public String addProf(String profId, boolean available,String firstName, String lastName, String profilePic,
                             String mobilePhone, String email, String gender, String address, String username, String password, String secretQuestion,
                             String secretAnswer, boolean active) {


        Professor professor = new Professor();

        professor.setAvailable(available);


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
        accessCredential.setActive(active);

        professor.setAccessCredential(accessCredential);


        try {
            entityManager.getTransaction().begin();
            entityManager.persist(professor);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            e.printStackTrace();

            String code = DatabaseUtil.getSqlErrorCode(e);
            System.out.println("Error creating professor: '{}'" + code);


            return DatabaseUtil.getCauseMessage(e);
        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        }
        return null;
    }

    public void close() {
        entityManager.close();
    }



    /**
     * Assign competency Course to a prof
     *
     * @param profUUID
     * @param courseUUID
     * @return
     */
    public boolean addProfCompetentCourse(String profUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Professor.getByUUID");
            query.setParameter("uuid", profUUID);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            professor.getCompetentCourseList().add(course);

            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * remove competent Course from a prof
     *
     * @param profUUID
     * @param courseUUID
     * @return
     */
    public boolean removeProfCompetentCourse(String profUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Professor.getByUUID");
            query.setParameter("uuid", profUUID);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            List<Course> courseList = professor.getCompetentCourseList();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            professor.setCompetentCourseList(courseList);
            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }



    /**
     * Assign teaching Course to a prof
     *
     * @param profUUID
     * @param courseUUID
     * @return
     */
    public boolean addProfTeachingCourse(String profUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Professor.getByUUID");
            query.setParameter("uuid", profUUID);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            professor.getTeachingCourseList().add(course);

            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * remove teaching Course from a prof
     *
     * @param profUUID
     * @param courseUUID
     * @return
     */
    public boolean removeProfTeachingCourse(String profUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Professor.getByUUID");
            query.setParameter("uuid", profUUID);
            Professor professor = (Professor) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            List<Course> courseList = professor.getTeachingCourseList();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            professor.setTeachingCourseList(courseList);
            entityManager.merge(professor);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }



}
