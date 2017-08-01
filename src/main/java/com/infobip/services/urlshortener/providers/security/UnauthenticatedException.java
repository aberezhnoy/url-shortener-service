package com.infobip.services.urlshortener.providers.security;

/**
 * Created by andrew
 */
public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException(String description) {
        super(description);
    }
}
