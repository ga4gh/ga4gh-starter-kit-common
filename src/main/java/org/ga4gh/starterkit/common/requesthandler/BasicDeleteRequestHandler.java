package org.ga4gh.starterkit.common.requesthandler;

import java.io.Serializable;

import org.ga4gh.starterkit.common.exception.ConflictException;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.ga4gh.starterkit.common.hibernate.exception.EntityDoesntExistException;
import org.ga4gh.starterkit.common.hibernate.exception.EntityExistsException;

public class BasicDeleteRequestHandler<I extends Serializable, T extends HibernateEntity<I>> implements RequestHandler<T> {

    private Class<T> entityClass;
    private I id;
    private HibernateUtil hibernateUtil;

    public BasicDeleteRequestHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public BasicDeleteRequestHandler<I, T> prepare(I id) {
        this.id = id;
        return this;
    }

    public T handleRequest() {
        try {
            hibernateUtil.deleteEntityObject(entityClass, id);
            return hibernateUtil.readEntityObject(entityClass, id, false); // should be null
        } catch (EntityDoesntExistException ex) {
            throw new ConflictException(ex.getMessage());
        } catch (EntityExistsException ex) {
            throw new ConflictException(ex.getMessage());
        }
        
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
}
