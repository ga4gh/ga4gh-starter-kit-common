package org.ga4gh.starterkit.common.requesthandler;

import java.io.Serializable;

import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;

public class BasicUpdateRequestHandler<I extends Serializable, T extends HibernateEntity<I>> implements RequestHandler<T> {

    private Class<T> entityClass;
    private I oldId;
    private I newId;
    private T newObject;
    private HibernateUtil hibernateUtil;

    public BasicUpdateRequestHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public BasicUpdateRequestHandler<I, T> prepare(I oldId, T newObject) {
        this.oldId = oldId;
        this.newId = newObject.getId();
        this.newObject = newObject;
        return this;
    }

    public T handleRequest() {
        hibernateUtil.updateEntityObject(entityClass, oldId, newId, newObject);
        return hibernateUtil.readEntityObject(entityClass, newId, true);
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
}
