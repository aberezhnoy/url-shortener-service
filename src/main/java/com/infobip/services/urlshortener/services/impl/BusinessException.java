package com.infobip.services.urlshortener.services.impl;

/**
 * Created by andrew
 */
public class BusinessException extends Exception {
    private final int statusCode;

    public BusinessException(String description) {
        super(description);
        statusCode = 500;
    }

    public BusinessException(String description, int statusCode) {
        super(description);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
