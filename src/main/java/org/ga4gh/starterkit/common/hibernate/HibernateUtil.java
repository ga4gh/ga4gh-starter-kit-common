package org.ga4gh.starterkit.common.hibernate;

import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
import org.ga4gh.starterkit.common.hibernate.exception.EntityExistsException;
import org.ga4gh.starterkit.common.hibernate.exception.EntityMismatchException;
import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.hibernate.exception.EntityDoesntExistException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private boolean configured;
    private DatabaseProps databaseProps;
    private List<Class<? extends HibernateEntity<? extends Serializable>>> annotatedClasses;
    private SessionFactory sessionFactory;
    
    public HibernateUtil() {
        configured = false;
    }

    @PostConstruct
    public void buildSessionFactory() {
        
        try {
            Configuration configuration = new Configuration();
            configuration.setProperties(getDatabaseProps().getAllProperties());
            for (Class<? extends HibernateEntity<? extends Serializable>> annotatedClass : getAnnotatedClasses()) {
                configuration.addAnnotatedClass(annotatedClass);
            }
            setSessionFactory(configuration.buildSessionFactory());
            setConfigured(true);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /* CRUD Methods */

    public <I extends Serializable, T extends HibernateEntity<I>> void createEntityObject(Class<T> entityClass, T newObject) throws EntityExistsException {
        Session session = newTransaction();
        try {
            T existingObject = session.get(entityClass, newObject.getId());
            if (existingObject != null) {
                endTransaction(session);
                throw new EntityExistsException("A(n) " + entityClass.getSimpleName() + " already exists at id " + newObject.getId());
            }
            session.save(newObject);
        } catch (Exception ex) {
            // any errors need to be caught so the transaction can be closed
            endTransaction(session);
            throw ex;
        } finally {
            endTransaction(session);
        }
    }

    public <I extends Serializable, T extends HibernateEntity<I>> T readEntityObject(Class<T> entityClass, I id, boolean loadRelations) throws HibernateException {
        Session session = newTransaction();
        T object;

        try {
            object = session.get(entityClass, id);
            if (object != null && loadRelations) {
                object.loadRelations();
            }
        } catch (Exception ex) {
            // any errors need to be caught so the transaction can be closed
            endTransaction(session);
            throw ex;
        } finally {
            endTransaction(session);
        }

        return object;
    }

    public <I extends Serializable, T extends HibernateEntity<I>> void updateEntityObject(Class<T> entityClass, I id, T newObject) throws EntityMismatchException, EntityDoesntExistException {
        if (!newObject.getId().equals(id)) {
            throw new EntityMismatchException("Update requested at id " + id + ", but new " + entityClass.getSimpleName() + " has an id of " + newObject.getId());
        }

        Session session = newTransaction();
        try {
            T oldObject = session.get(entityClass, id);
            if (oldObject == null) {
                endTransaction(session);
                throw new EntityDoesntExistException("No " + entityClass.getSimpleName() + " at id " + id);
            }
            session.update(session.merge(newObject));
        } catch (Exception ex) {
            // any errors need to be caught so the transaction can be closed
            endTransaction(session);
            throw ex;
        } finally {
            endTransaction(session);
        }
    }

    public <I extends Serializable, T extends HibernateEntity<I>> void deleteEntityObject(Class<T> entityClass, I id) throws EntityDoesntExistException, EntityExistsException {
        Session session = newTransaction();
        try {
            T object = session.get(entityClass, id);
            if (object == null) {
                endTransaction(session);
                throw new EntityDoesntExistException("No " + entityClass.getSimpleName() + " at id " + id);
            }
            session.delete(object);
            endTransaction(session);

            session = newTransaction();
            object = session.get(entityClass, id);
            if (object != null) {
                endTransaction(session);
                throw new EntityExistsException(entityClass.getSimpleName() + " at id " + id + " was not successfully deleted");
            }
        } catch (Exception ex) {
            // any errors need to be caught so the transaction can be closed
            endTransaction(session);
            throw ex;
        } finally {
            endTransaction(session);
        }
    }

    /* Convenience methods */

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

    public void setDatabaseProps(DatabaseProps databaseProps) {
        this.databaseProps = databaseProps;
    }

    public DatabaseProps getDatabaseProps() {
        return databaseProps;
    }

    public void setAnnotatedClasses(List<Class<? extends HibernateEntity<? extends Serializable>>> annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

    public List<Class<? extends HibernateEntity<? extends Serializable>>> getAnnotatedClasses() {
        return annotatedClasses;
    }

    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}