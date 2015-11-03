package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.AccessCredential;
import com.cs6310.backend.model.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by nelson on 11/3/15.
 */
public class AuthenticatorManager {


    private EntityManager entityManager;

    public AuthenticatorManager() {
        entityManager = DatabaseUtil.getEntityManager();
    }

    /**
     * Authenticate a user
     *
     * @param username
     * @param password
     * @return
     */
    public Student authenticateStudent(String username, String password) {
        Query q = entityManager
                .createNamedQuery("AccessCredential.findByUsername");
        q.setParameter("username", username);

        if (q.getResultList() == null || q.getResultList().size() <= 0) {
            return null;
        }

        try {
            AccessCredential accessCredential = (AccessCredential) q
                    .getSingleResult();
            if (accessCredential != null) {
                if (accessCredential.getPassword().equals(password)) {
                    Gson gson = new GsonBuilder().setPrettyPrinting()
                            .excludeFieldsWithoutExposeAnnotation().create();

                    System.out.println(gson.toJson(accessCredential));

                    Query query = entityManager
                            .createNamedQuery("Student.findByCredentials");
                    query.setParameter("accessCredential", accessCredential);

                    Student student = (Student) query.getSingleResult();
                    if (student != null) {
                        return student;
                    }

                } else {
                    return null;
                }
            } else {
                System.out.println("No matching credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
