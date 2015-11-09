package com.cs6310.backend.cms;

import com.cs6310.backend.helpers.DatabaseUtil;
import com.cs6310.backend.model.AccessCredential;

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
    public Object authenticate(String username, String password) {
        Query q = entityManager
                .createNamedQuery("com.cs6310.backend.model.AccessCredential.findByUsername");
        q.setParameter("username", username);

        if (q.getResultList() == null || q.getResultList().size() <= 0) {
            return null;
        }

        try {
            AccessCredential accessCredential = (AccessCredential) q
                    .getSingleResult();
            if (accessCredential != null) {
                if (accessCredential.getPassword().equals(password)) {

                    if (accessCredential != null) {
                        return accessCredential;
                    }

                } else {
                    return null;
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}

