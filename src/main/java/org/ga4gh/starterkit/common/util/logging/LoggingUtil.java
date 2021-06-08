package org.ga4gh.starterkit.common.util.logging;

import javax.annotation.PostConstruct;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import org.springframework.beans.factory.annotation.Autowired;

public class LoggingUtil {

    private static final String MESSAGE_FORMAT = "%date{yyyy-MM-dd HH:mm:ss} [%p] %message%n";

    @Autowired
    private ServerProps serverProps;

    private boolean configured;

    private Logger logger;

    public LoggingUtil() {
        configured = false;
    }

    public void trace(String msg) {
        logger.trace(msg);
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    @PostConstruct
    public void buildLogger() {
        setLogger((Logger)LoggerFactory.getLogger(getClass()));
        
        LoggerContext loggerContext = logger.getLoggerContext();
        loggerContext.reset();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern(MESSAGE_FORMAT);
        encoder.start();

        if (getServerProps().getLogFile() == null) {
            ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
            appender.setContext(loggerContext);
            appender.setEncoder(encoder);
            appender.start();
            getLogger().addAppender(appender);
        } else {
            FileAppender<ILoggingEvent> appender = new FileAppender<>();
            appender.setContext(loggerContext);
            appender.setFile(serverProps.getLogFile());
            appender.setEncoder(encoder);
            appender.start();
            getLogger().addAppender(appender);
        }

        setLogLevel();
        setConfigured(true);
    }

    private void setLogLevel() {
        Level level;

        switch(serverProps.getLogLevel()) {
            case TRACE:
                level = Level.TRACE;
                break;
            case DEBUG:
                level = Level.DEBUG;
                break;
            case INFO:
                level = Level.INFO;
                break;
            case WARN:
                level = Level.WARN;
                break;
            case ERROR:
                level = Level.ERROR;
                break;
            default:
                level = Level.DEBUG;
                break;
        }
        getLogger().setLevel(level);
    }

    public void setServerProps(ServerProps serverProps) {
        this.serverProps = serverProps;
    }

    public ServerProps getServerProps() {
        return serverProps;
    }

    public void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public boolean getConfigured() {
        return configured;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }
}
