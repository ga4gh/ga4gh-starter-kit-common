package org.ga4gh.starterkit.common.testutil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateProps;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.ga4gh.starterkit.common.requesthandler.BasicCreateRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicDeleteRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicShowRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicUpdateRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ConfigurationProperties
public class TestSpringConfig {

    @Bean
    public List<Class<? extends HibernateEntity<? extends Serializable>>> getTestAnnotatedClasses() {
        List<Class<? extends HibernateEntity<? extends Serializable>>> annotatedClasses = new ArrayList<>();
        annotatedClasses.add(Student.class);
        return annotatedClasses;
    }

    @Bean
    public HibernateProps getTestHibernateProps() {
        return new HibernateProps();
    }

    @Bean
    public HibernateUtil getTestHibernateUtil(
        @Autowired List<Class<? extends HibernateEntity<? extends Serializable>>> annotatedClasses,
        @Autowired HibernateProps hibernateProps
    ) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        hibernateUtil.setAnnotatedClasses(annotatedClasses);
        hibernateUtil.setHibernateProps(hibernateProps);
        return hibernateUtil;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public BasicShowRequestHandler<String, Student> getShowStudentHandler(
        @Autowired HibernateUtil hibernateUtil
    ) {
        BasicShowRequestHandler<String, Student> handler = new BasicShowRequestHandler<>(Student.class);
        handler.setHibernateUtil(hibernateUtil);
        return handler;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public BasicCreateRequestHandler<String, Student> getCreateStudentHandler(
        @Autowired HibernateUtil hibernateUtil
    ) {
        BasicCreateRequestHandler<String, Student> handler = new BasicCreateRequestHandler<>(Student.class);
        handler.setHibernateUtil(hibernateUtil);
        return handler;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public BasicUpdateRequestHandler<String, Student> getUpdateStudentHandler(
        @Autowired HibernateUtil hibernateUtil
    ) {
        BasicUpdateRequestHandler<String, Student> handler = new BasicUpdateRequestHandler<>(Student.class);
        handler.setHibernateUtil(hibernateUtil);
        return handler;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public BasicDeleteRequestHandler<String, Student> getDeleteStudentHandler(
        @Autowired HibernateUtil hibernateUtil
    ) {
        BasicDeleteRequestHandler<String, Student> handler = new BasicDeleteRequestHandler<>(Student.class);
        handler.setHibernateUtil(hibernateUtil);
        return handler;
    }
}
