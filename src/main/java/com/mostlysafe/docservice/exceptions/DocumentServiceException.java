package com.mostlysafe.docservice.exceptions;

public class DocumentServiceException extends RuntimeException {
    public DocumentServiceException () {
        super();
    }
    public DocumentServiceException (final String message) {
        super(message);
    }
    public DocumentServiceException (final String message, final Throwable cause) {
        super(message, cause);
    }
    public DocumentServiceException (final Throwable cause) {
        super(cause);
    }

}

