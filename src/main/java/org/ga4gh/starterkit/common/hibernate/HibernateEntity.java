package org.ga4gh.starterkit.common.hibernate;

public interface HibernateEntity {

    public void setId(String id);
    public String getId();
    public void loadRelations();
    
}
