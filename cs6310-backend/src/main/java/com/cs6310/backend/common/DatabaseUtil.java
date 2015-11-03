package com.cs6310.backend.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseUtil {
//	private static Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    public static EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();

        return em;
    }

    public static String getSqlErrorCode(Exception e) {
        String code = "";

        code = getCauseMessage(e);
        int index = code.indexOf("[code=");

        code = code.substring(index + 6, index + 10).trim();

        return code;
    }

    public static String getCauseMessage(Exception e) {
        String msg = "";
        for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
            msg = t.getMessage();
        }

        return msg;
    }

}
