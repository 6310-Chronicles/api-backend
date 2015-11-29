package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.Semester;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 11/3/15.
 */
public class SemesterManager {

    private final Logger logger;
    private EntityManager entityManager;

    public SemesterManager() {
        entityManager = DatabaseUtil.getEntityManager();
        logger = Logger.getLogger(AdministratorManager.class);
    }

    /**
     * Add new Semester details
     * @param name
     * @return
     */
    public String addSemester(String name, String year, String semesterId) {

        entityManager.getTransaction().begin();
        try {

            Semester semester = new Semester(semesterId, name);
            semester.setName(name);
            semester.setYear(year);
            semester.setSemesterId(semesterId);
            semester.setUuid(String.valueOf(UUID.randomUUID()));

            entityManager.persist(semester);
            entityManager.getTransaction().commit();

            return "OK";

        } catch (Exception e) {
            e.printStackTrace();

            return DatabaseUtil.getCauseMessage(e);
        }


    }


    /**
     * Get all persisted semesters
     *
     * @return
     */
    public List<Semester> getAllSemesters() {
        entityManager.getTransaction().begin();

        Query query = entityManager
                .createNamedQuery("com.cs6310.backend.model.Semester.getAll");

        entityManager.getTransaction().commit();
        if (query.getResultList() == null) {
            return null;
        } else {
            return query.getResultList();
        }
    }


    public Semester getSemester(String uuid) {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Semester.getByUUID");
            query.setParameter("uuid", uuid);

            Semester semester = (Semester) query.getSingleResult();


            if (semester != null) {
                return semester;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }

    public String removeSemester(String uuid) {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("com.cs6310.backend.model.Semester.getByUUID");
            query.setParameter("uuid", uuid);

            if (query.getSingleResult() != null) {

                Semester semester = (Semester) query.getSingleResult();
                entityManager.remove(semester);
                entityManager.getTransaction().commit();
                return null;

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

    public boolean deleteAllSemesters() {
        boolean resp = false;
        entityManager.getTransaction().begin();
        try {
            int deletedCount = entityManager.createQuery("DELETE FROM com.cs6310.backend.model.Semester").executeUpdate();

            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return resp;
    }


}
