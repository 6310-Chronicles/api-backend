package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.Course;
import com.cs6310.backend.model.Semester;
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
    public boolean addCourse(String courseId, boolean hasPrerequisite, boolean mustBeOffered, Integer courseName, Integer courseCredits, Integer maximumEnrollment,
                             Integer currentEnrollment) {
        boolean resp = false;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        entityManager.getTransaction().begin();
        try {

            Course course = new Course();
            course.setCourseId(courseId);
            course.setHasPrerequisite(hasPrerequisite);
            course.setMustBeOffered(mustBeOffered);
            course.setCourseName(courseName);
            course.setCourseCredits(courseCredits);
            course.setMaximumEnrollment(maximumEnrollment);
            course.setCurrentEnrollment(currentEnrollment);

            course.setUuid(String.valueOf(UUID.randomUUID()));

            entityManager.persist(course);
            entityManager.getTransaction().commit();
            resp = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
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
    public boolean updateCourse(String courseId, Boolean hasPrerequisite, Boolean mustBeOffered, Integer courseName, Integer courseCredits, Integer maximumEnrollment,
                                Integer currentEnrollment) {
        boolean resp = false;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        entityManager.getTransaction().begin();
        try {

            Course course = getCourseByUUID(courseId);
            if (hasPrerequisite != null)
                course.setHasPrerequisite(hasPrerequisite);
            if (mustBeOffered != null)
                course.setMustBeOffered(mustBeOffered);
            if (courseName != null)
                course.setCourseName(courseName);
            if (courseCredits != null)
                course.setCourseCredits(courseCredits);
            if (maximumEnrollment != null)
                course.setMaximumEnrollment(maximumEnrollment);
            if (currentEnrollment != null)
                course.setCurrentEnrollment(currentEnrollment);


            entityManager.merge(course);
            entityManager.getTransaction().commit();
            resp = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }


    /**
     * add  prerequisite Course to a course
     *
     * @param courseUUID1
     * @param courseUUID2
     * @return
     */
    public boolean addPrerequisiteToCourse(String courseUUID1, String courseUUID2) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Course.getByUUID");
            query.setParameter("uuid", courseUUID1);
            Course course = (Course) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID2);
            Course course2 = (Course) querycourse.getSingleResult();

            course.getListOfPrerequisiteCourses().add(course2);


            entityManager.merge(course);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * remove  prerequisite Course to a course
     *
     * @param courseUUID1
     * @param courseUUID2
     * @return
     */
    public boolean removePrerequisiteToCourse(String courseUUID1, String courseUUID2) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Course.getByUUID");
            query.setParameter("uuid", courseUUID1);
            Course course = (Course) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID2);
            Course course2 = (Course) querycourse.getSingleResult();

            course.getListOfPrerequisiteCourses().add(course2);


            List<Course> courseList = course.getListOfPrerequisiteCourses();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            course.setListOfPrerequisiteCourses(courseList);
            entityManager.merge(course);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * set  semester of a course
     *
     * @param courseUUID
     * @param semesterUUID
     * @return
     */
    public boolean setCourseSemester(String courseUUID, String semesterUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Course.getByUUID");
            query.setParameter("uuid", courseUUID);
            Course course = (Course) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Semester.getByUUID");
            querycourse.setParameter("uuid", semesterUUID);
            Semester semester = (Semester) querycourse.getSingleResult();

            course.getOfferedInSemester().add(semester);

            entityManager.merge(course);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * remove  semester of a course
     *
     * @param courseUUID
     * @param semesterUUID
     * @return
     */
    public boolean removeCourseSemester(String courseUUID, String semesterUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Course.getByUUID");
            query.setParameter("uuid", courseUUID);
            Course course = (Course) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Semester.getByUUID");
            querycourse.setParameter("uuid", semesterUUID);
            Semester semester = (Semester) querycourse.getSingleResult();

            course.getOfferedInSemester().add(semester);

            List<Semester> semesterList = course.getOfferedInSemester();
            int size = semesterList.size();

            for (int t = 0; t < size; t++) {
                Semester semester1 = semesterList.get(t);
                if (semester1.getUuid().equalsIgnoreCase(semester.getUuid()))
                    semesterList.remove(t);
            }
            course.setOfferedInSemester(semesterList);
            entityManager.merge(course);
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
    public Course getCourseByUUID(String id) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Course.getByUUID");
            query.setParameter("uuid", id);
            entityManager.getTransaction().commit();
            return (Course) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
