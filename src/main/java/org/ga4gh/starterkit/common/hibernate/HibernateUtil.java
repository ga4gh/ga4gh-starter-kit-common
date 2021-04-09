package org.ga4gh.starterkit.common.hibernate;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private boolean configured;
    private HibernateProps hibernateProps;
    private List<Class<? extends HibernateEntity>> annotatedClasses;
    private SessionFactory sessionFactory;
    
    public HibernateUtil() {
        configured = false;
    }

    @PostConstruct
    public void buildSessionFactory() {
        
        try {
            Configuration configuration = new Configuration();
            configuration.setProperties(getHibernateProps().getAllProperties());
            for (Class<? extends HibernateEntity> annotatedClass : getAnnotatedClasses()) {
                configuration.addAnnotatedClass(annotatedClass);
            }
            setSessionFactory(configuration.buildSessionFactory());
            setConfigured(true);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /* CRUD Methods */

    public <T extends HibernateEntity> T readEntityObject(Class<T> entityClass, String id, boolean loadRelations) throws HibernateException {
        Session session = newTransaction();
        T object = null;
        try {
            object = session.get(entityClass, id);
            if (object != null && loadRelations) {
                object.loadRelations();
            }
            endTransaction(session);
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
        return object;
    }

    public Session newTransaction() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return session;
    }

    public void endTransaction(Session session) {
        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
            session.close();
        }
    }

    public void shutdown() {
        getSessionFactory().close();
    }

    /* SETTERS AND GETTERS */

    private void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public boolean getConfigured() {
        return configured;
    }

    public void setHibernateProps(HibernateProps hibernateProps) {
        this.hibernateProps = hibernateProps;
    }

    public HibernateProps getHibernateProps() {
        return hibernateProps;
    }

    public void setAnnotatedClasses(List<Class<? extends HibernateEntity>> annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

    public List<Class<? extends HibernateEntity>> getAnnotatedClasses() {
        return annotatedClasses;
    }

    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}