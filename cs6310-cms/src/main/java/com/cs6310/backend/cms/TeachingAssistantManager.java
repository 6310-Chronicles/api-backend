package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.Course;
import com.cs6310.backend.model.Student;
import com.cs6310.backend.model.TeachingAssistant;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

/**
 * Created by nelson on 11/3/15.
 */


public class TeachingAssistantManager {

    private EntityManager entityManager;
    private Logger logger;

    public TeachingAssistantManager() {
        entityManager = DatabaseUtil.getEntityManager();
        logger = Logger.getLogger(StudentManager.class);

    }


    /**
     * Add a new TA and save to database
     *
     * @param studentId
     * @return
     */


    public String addTeachingAssistant(String studentId) {


        Student student = getStudentByUUID(studentId);

        TeachingAssistant ta = new TeachingAssistant();

        ta.setStudent(student);


        try {
            entityManager.getTransaction().begin();
            entityManager.persist(ta);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            e.printStackTrace();

            String code = DatabaseUtil.getSqlErrorCode(e);
            System.out.println("Error creating student: '{}'" + code);


            return DatabaseUtil.getCauseMessage(e);
        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        }
        return null;
    }

    /**
     * Assign competency Course to a ta
     *
     * @param taUUID
     * @param courseUUID
     * @return
     */
    public boolean addTACompetentCourse(String taUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("TeachingAssistant.getByUUID");
            query.setParameter("uuid", taUUID);
            TeachingAssistant teachingAssistant = (TeachingAssistant) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            teachingAssistant.getCompetency().add(course);

            entityManager.merge(teachingAssistant);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * remove competent Course from a TA
     *
     * @param taUUID
     * @param courseUUID
     * @return
     */
    public boolean removeTACompetentCourse(String taUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("TeachingAssistant.getByUUID");
            query.setParameter("uuid", taUUID);
            TeachingAssistant teachingAssistant = (TeachingAssistant) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            List<Course> courseList = teachingAssistant.getCompetency();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            teachingAssistant.setCompetency(courseList);
            entityManager.merge(teachingAssistant);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }



    /**
     * Assign assisting Course to a ta
     *
     * @param taUUID
     * @param courseUUID
     * @return
     */
    public boolean addTAAssistingCourse(String taUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("TeachingAssistant.getByUUID");
            query.setParameter("uuid", taUUID);
            TeachingAssistant teachingAssistant = (TeachingAssistant) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            teachingAssistant.getAssisting().add(course);

            entityManager.merge(teachingAssistant);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * remove assisting Course from a TA
     *
     * @param taUUID
     * @param courseUUID
     * @return
     */
    public boolean removeTAAssistingCourse(String taUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("TeachingAssistant.getByUUID");
            query.setParameter("uuid", taUUID);
            TeachingAssistant teachingAssistant = (TeachingAssistant) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            List<Course> courseList = teachingAssistant.getAssisting();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            teachingAssistant.setAssisting(courseList);
            entityManager.merge(teachingAssistant);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }





    /**
     * Get student by UUID
     *
     * @param id
     * @return
     */
    public Student getStudentByUUID(String id) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", id);
            entityManager.getTransaction().commit();
            return (Student) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
