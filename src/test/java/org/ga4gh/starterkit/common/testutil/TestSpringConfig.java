package org.ga4gh.starterkit.common.testutil;

import java.util.ArrayList;
import java.util.List;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateProps;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class TestSpringConfig {

    @Bean
    public List<Class<? extends HibernateEntity>> getTestAnnotatedClasses() {
        List<Class<? extends HibernateEntity>> annotatedClasses = new ArrayList<>();
        annotatedClasses.add(Student.class);
        return annotatedClasses;
    }

    @Bean
    public HibernateProps getTestHibernateProps() {
        return new HibernateProps();
    }

    @Bean
    public HibernateUtil getTestHibernateUtil(
        @Autowired List<Class<? extends HibernateEntity>> annotatedClasses,
        @Autowired HibernateProps hibernateProps
    ) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        hibernateUtil.setAnnotatedClasses(annotatedClasses);
        hibernateUtil.setHibernateProps(hibernateProps);
        return hibernateUtil;
    }
}
