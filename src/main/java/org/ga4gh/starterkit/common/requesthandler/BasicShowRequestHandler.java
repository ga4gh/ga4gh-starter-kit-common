package org.ga4gh.starterkit.common.requesthandler;

import java.io.Serializable;

import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;

public class BasicShowRequestHandler<I extends Serializable, T extends HibernateEntity<I>> implements RequestHandler<T> {

    private Class<T> entityClass;
    private I id;
    private HibernateUtil hibernateUtil;

    public BasicShowRequestHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public BasicShowRequestHandler<I, T> prepare(I id) {
        this.id = id;
        return this;
    }

    public T handleRequest() {
        return hibernateUtil.readEntityObject(entityClass, id, true);
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
}
