package org.ga4gh.starterkit.common.hibernate.exception;

public class EntityExistsException extends Exception {

    public static final long serialVersionUID = 1L;

    public EntityExistsException(String message) {
        super(message);
    }
}
