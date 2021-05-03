package org.ga4gh.starterkit.common.config;

import java.util.Properties;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.DRIVER_CLASS_NAME;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.URL;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.USERNAME;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.PASSWORD;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.POOL_SIZE;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.DIALECT;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.HBM2DDL_AUTO;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.SHOW_SQL;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.CURRENT_SESSION_CONTEXT_CLASS;
import static org.ga4gh.starterkit.common.constant.DatabasePropsDefaults.DATE_CLASS;

public class DatabaseProps {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String poolSize;
    private String dialect;
    private String hbm2ddlAuto;
    private String showSQL;
    private String currentSessionContextClass;
    private String dateClass;

    public DatabaseProps() {
        setAllDefaults();
    }

    public Properties getAllProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.connection.driver_class", getDriverClassName());
        props.setProperty("hibernate.connection.url", getUrl());

        if (!getUsername().equals("")) {
            props.setProperty("hibernate.connection.username", getUsername());
        }

        if (!getPassword().equals("")) {
            props.setProperty("hibernate.connection.password", getPassword());
        }

        props.setProperty("hibernate.connection.pool_size", getPoolSize());
        props.setProperty("hibernate.dialect", getDialect());

        if (!getHbm2ddlAuto().equals("")) {
            props.setProperty("hibernate.hbm2ddl.auto", getHbm2ddlAuto());
        }

        props.setProperty("hibernate.show_sql", getShowSQL());
        props.setProperty("hibernate.current_session_context_class", getCurrentSessionContextClass());

        if (!getDateClass().equals("")) {
            props.setProperty("hibernate.connection.date_class", getDateClass());
        }

        return props;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPoolSize(String poolSize) {
        this.poolSize = poolSize;
    }

    public String getPoolSize() {
        return poolSize;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getDialect() {
        return dialect;
    }

    public void setHbm2ddlAuto(String hbm2ddlAuto) {
        this.hbm2ddlAuto = hbm2ddlAuto;
    }

    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    public void setShowSQL(String showSQL) {
        this.showSQL = showSQL;
    }

    public String getShowSQL() {
        return showSQL;
    }

    public void setCurrentSessionContextClass(String currentSessionContextClass)  {
        this.currentSessionContextClass = currentSessionContextClass;
    }

    public String getCurrentSessionContextClass() {
        return currentSessionContextClass;
    }

    public void setDateClass(String dateClass) {
        this.dateClass = dateClass;
    }

    public String getDateClass() {
        return dateClass;
    }

    private void setAllDefaults() {
        setDriverClassName(DRIVER_CLASS_NAME);
        setUrl(URL);
        setUsername(USERNAME);
        setPassword(PASSWORD);
        setPoolSize(POOL_SIZE);
        setDialect(DIALECT);
        setHbm2ddlAuto(HBM2DDL_AUTO);
        setShowSQL(SHOW_SQL);
        setCurrentSessionContextClass(CURRENT_SESSION_CONTEXT_CLASS);
        setDateClass(DATE_CLASS);
    }
}
