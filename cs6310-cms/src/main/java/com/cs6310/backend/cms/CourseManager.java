package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.helpers.Utils;
import com.cs6310.backend.model.Course;
import com.cs6310.backend.model.Semester;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 11/3/15.
 */
public class CourseManager {

    private final Logger logger;
    private EntityManager entityManager;

    public CourseManager() {
        entityManager = DatabaseUtil.getEntityManager();
        logger = Logger.getLogger(AdministratorManager.class);
    }

    /**
     * Add new Course details
     *
     * @param courseId
     * @param hasPrerequisite
     * @param mustBeOffered
     * @param courseName
     * @param courseCredits
     * @param maximumEnrollment
     * @param currentEnrollment
     * @return
     */
    public String addCourse(String courseId, String hasPrerequisite, String mustBeOffered, String courseName, String courseCredits, String maximumEnrollment,
                            String currentEnrollment, String priority) {


        entityManager.getTransaction().begin();
        try {

            Course course = new Course();
            course.setCourseId(courseId);
            course.setHasPrerequisite(Utils.convertStringToBool(hasPrerequisite));
            course.setMustBeOffered(Utils.convertStringToBool(mustBeOffered));
            course.setCourseName(courseName);
            course.setCourseCredits(Utils.convertIntegerToString(courseCredits));
            course.setMaximumEnrollment(Utils.convertIntegerToString(maximumEnrollment));
            course.setCurrentEnrollment(Utils.convertIntegerToString(currentEnrollment));
            course.setPriority(Utils.convertIntegerToString(priority));
            course.setUuid(String.valueOf(UUID.randomUUID()));

            entityManager.persist(course);

            entityManager.getTransaction().commit();


        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);

        } finally {
            entityManager.close();
        }


        return null;

    }


    /**
     * update new Course details
     *
     * @param courseId
     * @param hasPrerequisite
     * @param mustBeOffered
     * @param courseName
     * @param courseCredits
     * @param maximumEnrollment
     * @param currentEnrollment
     * @return
     */
    public String updateCourse(String uuid, String courseId, String hasPrerequisite, String mustBeOffered, String courseName, String courseCredits, String maximumEnrollment,
                               String currentEnrollment, String priority) {

        entityManager.getTransaction().begin();
        try {


            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            query.setParameter("uuid", uuid);
            Course course = (Course) query.getSingleResult();


            if (hasPrerequisite != null)
                course.setHasPrerequisite(Utils.convertStringToBool(hasPrerequisite));
            if (mustBeOffered != null)
                course.setMustBeOffered(Utils.convertStringToBool(mustBeOffered));
            if (courseName != null)
                course.setCourseName(courseName);
            if (courseCredits != null)
                course.setCourseCredits(Utils.convertIntegerToString(courseCredits));
            if (maximumEnrollment != null)
                course.setMaximumEnrollment(Utils.convertIntegerToString(maximumEnrollment));
            if (currentEnrollment != null)
                course.setCurrentEnrollment(Utils.convertIntegerToString(currentEnrollment));
            if (priority != null)
                course.setPriority(Utils.convertIntegerToString(priority));


            entityManager.merge(course);
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
     * add  prerequisite Course to a course
     *
     * @param courseUUID1
     * @param courseUUID2
     * @return
     */
    public String addPrerequisiteToCourse(String courseUUID1, String courseUUID2) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            query.setParameter("uuid", courseUUID1);

            Course course = (Course) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID2);
            Course course2 = (Course) querycourse.getSingleResult();

            if (!course2.getUuid().equalsIgnoreCase(course.getUuid())) {
                course.getListOfPrerequisiteCourses().add(course2);

                entityManager.merge(course);
                entityManager.getTransaction().commit();
            }

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        } finally {
            entityManager.close();
        }


    }


    /**
     * remove  prerequisite Course to a course
     *
     * @param courseUUID1
     * @param courseUUID2
     * @return
     */
    public String removePrerequisiteToCourse(String courseUUID1, String courseUUID2) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            query.setParameter("uuid", courseUUID1);
            Course course = (Course) query.getSingleResult();

            List<Course> courseList = course.getListOfPrerequisiteCourses();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {

                Course course1 = courseList.get(t);// ;

                if (course1.getUuid().equalsIgnoreCase(courseUUID2)) {

                    course.getListOfPrerequisiteCourses().remove(t);

                    break;
                }
            }
            entityManager.merge(course);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }

    }


    /**
     * set  semester of a course
     *
     * @param courseUUID
     * @param semesterUUID
     * @return
     */
    public String setCourseSemester(String courseUUID, String semesterUUID) {
        try {

            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            query.setParameter("uuid", courseUUID);
            Course course = (Course) query.getSingleResult();


            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Semester.getByUUID");
            querycourse.setParameter("uuid", semesterUUID);

            Semester semester = (Semester) querycourse.getSingleResult();


            course.getOfferedInSemester().add(semester);

            entityManager.merge(course);

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
     * remove  semester of a course
     *
     * @param courseUUID
     * @param semesterUUID
     * @return
     */
    public String removeCourseSemester(String courseUUID, String semesterUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            query.setParameter("uuid", courseUUID);
            Course course = (Course) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Semester.getByUUID");
            querycourse.setParameter("uuid", semesterUUID);
            Semester semester = (Semester) querycourse.getSingleResult();


            List<Semester> semesterList = course.getOfferedInSemester();
            int size = semesterList.size();

            for (int t = 0; t < size; t++) {
                Semester semester1 = semesterList.get(t);

                if (semester1.getUuid().equalsIgnoreCase(semester.getUuid()))
                    course.getOfferedInSemester().remove(t);
            }
            entityManager.merge(course);
            entityManager.getTransaction().commit();

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseUtil.getCauseMessage(e);
        }


    }

    /**
     * Get Course by UUID
     *
     * @param id
     * @return
     */
    public Course getCourseByUUID(String id) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            query.setParameter("uuid", id);

            return (Course) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Delete Course by UUID
     *
     * @param id
     * @return
     */
    public String deleteCourseByUUID(String id) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("com.cs6310.backend.model.Course.getByUUID");
            query.setParameter("uuid", id);

            entityManager.remove(query.getSingleResult());
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
     * Get All persisted users
     *
     * @return
     */
    public List<Course> getAll() {
        entityManager.getTransaction().begin();
        Query query = entityManager
                .createNamedQuery("com.cs6310.backend.model.Course.getAll");
        entityManager.getTransaction().commit();
        if (query.getResultList() != null) {
            return query.getResultList();
        } else {
            return null;
        }
    }


}
