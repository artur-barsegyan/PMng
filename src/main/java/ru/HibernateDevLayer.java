package ru;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

import java.util.List;

/*  About handling Hibernate exceptions:
    No exception thrown by an entity manager can be treated as recoverable and
    you should immediately rollback the database transaction */

public class HibernateDevLayer<T>  {
    private static Logger logger = LogManager.getLogger(HibernateDevLayer.class);

    private Session session = null;
    private Transaction tx = null;

    public HibernateDevLayer() {
        try {
            session = AppResources.getDBSession();
            tx = session.beginTransaction();
        } catch (JDBCConnectionException e) {
            logger.error("Problems with connection to DB");
            logger.error(e.getMessage());

            throw e;
        }
    }
    public Integer saveEntity(Object entity) {
        try {
            Integer entityID = (Integer) session.save(entity);
            tx.commit();

            return entityID;
        } catch (ConstraintViolationException e) {
            tx.rollback();

            logger.error(e.getCause().getMessage());
            throw e;
        } catch (JDBCConnectionException e) {
            tx.rollback();

            logger.error("Problems with connection to DB");
            logger.error(e.getMessage());
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Object getEntity(Class entityClass, Integer id) {
        try {
            Object entity = session.get(entityClass, id);
            tx.commit();

            return entity;
        } catch (JDBCConnectionException e) {
            tx.rollback();

            logger.error("Problems with connection to DB");
            logger.error(e.getMessage());
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public List<T> getEntityListByQuery(String queryString) {
        try {
            List<T> entity = session.createQuery(queryString).list();
            tx.commit();

            return entity;
        } catch (JDBCConnectionException e) {
            tx.rollback();

            logger.error("Problems with connection to DB");
            logger.error(e.getMessage());
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }
}