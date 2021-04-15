package org.ga4gh.starterkit.common.requesthandler;

import java.io.Serializable;

import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;

public class BasicCreateRequestHandler<I extends Serializable, T extends HibernateEntity<I>> implements RequestHandler<T> {

    private Class<T> entityClass;
    private T newObject;
    private HibernateUtil hibernateUtil;

    public BasicCreateRequestHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public BasicCreateRequestHandler<I, T> prepare(T newObject) {
        this.newObject = newObject;
        return this;
    }

    public T handleRequest() {
        // create the object in the db, then read it from the db and return
        hibernateUtil.createEntityObject(entityClass, newObject);
        return hibernateUtil.readEntityObject(entityClass, newObject.getId(), true);
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
}
