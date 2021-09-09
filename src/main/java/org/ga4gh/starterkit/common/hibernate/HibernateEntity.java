package org.ga4gh.starterkit.common.hibernate;

import java.io.Serializable;

public interface HibernateEntity<I extends Serializable> {

    public void setId(I id);
    public I getId();
    public void loadRelations();
}
