package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.AccessCredential;
import com.cs6310.backend.model.Course;
import com.cs6310.backend.model.PersonDetails;
import com.cs6310.backend.model.Student;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.UUID;

public class StudentManager {

    private EntityManager entityManager;
    private Logger logger;

    public StudentManager() {
        entityManager = DatabaseUtil.getEntityManager();
        logger = Logger.getLogger(StudentManager.class);

    }

    /**
     * Add a new students and save to database
     *
     * @param studentId
     * @param studentStatus
     * @param maxCourses
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


    public String addStudent(String studentId, String studentStatus, Integer maxCourses, String firstName, String lastName, String profilePic,
                             String mobilePhone, String email, String gender, String address, String username, String password, String secretQuestion,
                             String secretAnswer, boolean active) {


        Student student = new Student();
        student.setStudentId(studentId);
        student.setStudentStatus(studentStatus);
        student.setMaxCourses(maxCourses);

        PersonDetails personDetails = new PersonDetails();
        personDetails.setFirstName(firstName);
        personDetails.setLastName(lastName);
        personDetails.setMobilePhone(mobilePhone);
        personDetails.setEmail(email);
        personDetails.setGender(gender);
        personDetails.setAddress(address);
        personDetails.setProfilePic(profilePic);

        personDetails.setUuid(String.valueOf(UUID.randomUUID()));

        student.setPersonDetails(personDetails);
        student.setUuid(String.valueOf(UUID.randomUUID()));


        AccessCredential accessCredential = new AccessCredential();
        accessCredential.setUsername(username);
        accessCredential.setPassword(password);
        accessCredential.setSecretQuestion(secretQuestion);
        accessCredential.setSecretAnswer(secretAnswer);
        accessCredential.setActive(active);

        student.setAccessCredential(accessCredential);


        try {
            entityManager.getTransaction().begin();
            entityManager.persist(student);
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

    public void close() {
        entityManager.close();
    }


    /**
     * Get All persisted students
     *
     * @return
     */
    public List<Student> getAllStudents() {
        entityManager.getTransaction().begin();
        Query query = entityManager
                .createNamedQuery("Student.getAll");
        entityManager.getTransaction().commit();
        if (query.getResultList() != null) {
            return query.getResultList();
        } else {
            return null;
        }
    }

    /**
     * Get student by studentId
     *
     * @param id
     * @return
     */
    public Student getUserByStudentId(String id) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByStudentId");
            query.setParameter("studentId", id);
            entityManager.getTransaction().commit();
            return (Student) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Get student by UUID
     *
     * @param id
     * @return
     */
    public Student getUserByUUID(String id) {
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


    /**
     * Get student by studentId
     *
     * @param id
     * @return
     */
    public boolean deleteUserByStudentId(String id) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByStudentId");
            query.setParameter("studentId", id);
            Student student = (Student) query.getSingleResult();
            entityManager.remove(student);
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
    public boolean deleteUserByUUID(String id) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", id);
            Student student = (Student) query.getSingleResult();
            entityManager.remove(student);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Assign completed Course to a student
     *
     * @param studentUUID
     * @param courseUUID
     * @return
     */
    public boolean addStudentsCompletedCourse(String studentUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", studentUUID);
            Student student = (Student) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            student.getCompletedCourses().add(course);

            entityManager.merge(student);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Assign completed Course to a student
     *
     * @param studentUUID
     * @param courseUUID
     * @return
     */
    public boolean removeStudentsCompletedCourse(String studentUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", studentUUID);
            Student student = (Student) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            List<Course> courseList = student.getCompletedCourses();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            student.setCompletedCourses(courseList);
            entityManager.merge(student);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Assign recommended Course to a student
     *
     * @param studentUUID
     * @param courseUUID
     * @return
     */
    public boolean addStudentsRecommendedCourse(String studentUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", studentUUID);
            Student student = (Student) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            student.getRecommendedCourses().add(course);

            entityManager.merge(student);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Assign recommended Course to a student
     *
     * @param studentUUID
     * @param courseUUID
     * @return
     */
    public boolean removeStudentsRecommendedCourse(String studentUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", studentUUID);
            Student student = (Student) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();
            List<Course> courseList = student.getRecommendedCourses();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            student.setRecommendedCourses(courseList);
            entityManager.merge(student);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Assign progress Course to a student
     *
     * @param studentUUID
     * @param courseUUID
     * @return
     */
    public boolean addStudentsProgressCourse(String studentUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", studentUUID);
            Student student = (Student) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            student.getCoursesInProgress().add(course);

            entityManager.merge(student);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Assign progress Course to a student
     *
     * @param studentUUID
     * @param courseUUID
     * @return
     */
    public boolean removeStudentsProgressCourse(String studentUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", studentUUID);
            Student student = (Student) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();
            List<Course> courseList = student.getCoursesInProgress();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            student.setCoursesInProgress(courseList);

            entityManager.merge(student);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Assign ToBeOptimized Course to a student
     *
     * @param studentUUID
     * @param courseUUID
     * @return
     */
    public boolean addStudentsToBeOptimizedCourse(String studentUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", studentUUID);
            Student student = (Student) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();

            student.getPrefferedCoursesToBeOptimized().add(course);

            entityManager.merge(student);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Assign ToBeOptimized Course to a student
     *
     * @param studentUUID
     * @param courseUUID
     * @return
     */
    public boolean removeStudentsToBeOptimizedCourse(String studentUUID, String courseUUID) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager
                    .createNamedQuery("Student.getByUUID");
            query.setParameter("uuid", studentUUID);
            Student student = (Student) query.getSingleResult();

            Query querycourse = entityManager
                    .createNamedQuery("Course.getByUUID");
            querycourse.setParameter("uuid", courseUUID);
            Course course = (Course) querycourse.getSingleResult();
            List<Course> courseList = student.getPrefferedCoursesToBeOptimized();
            int size = courseList.size();

            for (int t = 0; t < size; t++) {
                Course course1 = courseList.get(t);
                if (course1.getUuid().equalsIgnoreCase(course.getUuid()))
                    courseList.remove(t);
            }
            student.setPrefferedCoursesToBeOptimized(courseList);

            entityManager.merge(student);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * update students in the database
     *
     * @param studentId
     * @param studentStatus
     * @param maxCourses
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


    public String updateStudent(String studentId, String studentStatus, Integer maxCourses, String firstName, String lastName, String profilePic,
                                String mobilePhone, String email, String gender, String address, String username, String password, String secretQuestion,
                                String secretAnswer, boolean active) {

        entityManager.getTransaction().begin();
        Query query = entityManager
                .createNamedQuery("Student.getByStudentId");
        query.setParameter("studentId", studentId);
        Student student = (Student) query.getSingleResult();
        entityManager.getTransaction().commit();


        if (studentId != null)
            student.setStudentId(studentId);
        if (studentStatus != null)
            student.setStudentStatus(studentStatus);
        if (maxCourses != null)
            student.setMaxCourses(maxCourses);

        PersonDetails personDetails = student.getPersonDetails();
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


        student.setPersonDetails(personDetails);


        AccessCredential accessCredential = student.getAccessCredential();

        if (username != null)
            accessCredential.setUsername(username);
        if (password != null)
            accessCredential.setPassword(password);
        if (secretQuestion != null)
            accessCredential.setSecretQuestion(secretQuestion);
        if (secretAnswer != null)
            accessCredential.setSecretAnswer(secretAnswer);


        student.setAccessCredential(accessCredential);


        try {
            entityManager.getTransaction().begin();
            entityManager.merge(student);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            e.printStackTrace();

            String code = DatabaseUtil.getSqlErrorCode(e);

            logger.info("Error creating student: '{}'" + code);


            return DatabaseUtil.getCauseMessage(e);
        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        }
        return null;
    }


}
