package org.ga4gh.starterkit.common.hibernate.exception;

public class EntityMismatchException extends Exception {

    public static final long serialVersionUID = 1L;

    public EntityMismatchException(String message) {
        super(message);
    }
}
