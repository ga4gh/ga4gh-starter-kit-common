package org.ga4gh.starterkit.common.requesthandler;

import java.io.Serializable;

import org.ga4gh.starterkit.common.exception.BadRequestException;
import org.ga4gh.starterkit.common.exception.ConflictException;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.ga4gh.starterkit.common.hibernate.exception.EntityDoesntExistException;
import org.ga4gh.starterkit.common.hibernate.exception.EntityMismatchException;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class BasicUpdateRequestHandler<I extends Serializable, T extends HibernateEntity<I>> implements RequestHandler<T> {

    private Class<T> entityClass;
    private I id;
    private T newObject;
    private HibernateUtil hibernateUtil;

    @Autowired
    private LoggingUtil loggingUtil;

    public BasicUpdateRequestHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public BasicUpdateRequestHandler<I, T> prepare(I id, T newObject) {
        this.id = id;
        this.newObject = newObject;
        return this;
    }

    public T handleRequest() {
        try {
            hibernateUtil.updateEntityObject(entityClass, id, newObject);
            return hibernateUtil.readEntityObject(entityClass, id, true);
        } catch (EntityMismatchException ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        } catch (EntityDoesntExistException ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            throw new ConflictException(ex.getMessage());
        }
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
}
