package com.infobip.services.urlshortener.providers.security;

/**
 * Created by andrew
 */
public interface AuthenticationProvider {
    public boolean isAuthenticationValid(String accountId, String credentials);
}
