package org.ga4gh.starterkit.common.hibernate.exception;

public class EntityDoesntExistException extends Exception {

    public static final long serialVersionUID = 1L;

    public EntityDoesntExistException(String message) {
        super(message);
    }
}

