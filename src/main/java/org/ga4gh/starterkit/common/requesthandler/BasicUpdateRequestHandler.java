package org.ga4gh.starterkit.common.requesthandler;

import java.io.Serializable;

import org.ga4gh.starterkit.common.exception.BadRequestException;
import org.ga4gh.starterkit.common.exception.ConflictException;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.ga4gh.starterkit.common.hibernate.exception.EntityDoesntExistException;
import org.ga4gh.starterkit.common.hibernate.exception.EntityMismatchException;

public class BasicUpdateRequestHandler<I extends Serializable, T extends HibernateEntity<I>> implements RequestHandler<T> {

    private Class<T> entityClass;
    private I id;
    private T newObject;
    private HibernateUtil hibernateUtil;

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
            throw new BadRequestException(ex.getMessage());
        } catch (EntityDoesntExistException ex) {
            throw new ConflictException(ex.getMessage());
        }
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
}
