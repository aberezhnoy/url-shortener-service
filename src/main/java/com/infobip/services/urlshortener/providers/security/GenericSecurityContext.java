package com.infobip.services.urlshortener.providers.security;

import com.sun.security.auth.UserPrincipal;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by andrew
 */
public class GenericSecurityContext implements SecurityContext {
    private UserPrincipal principal;

    public GenericSecurityContext(String principal) {
        if (principal != null) {
            this.principal = new UserPrincipal(principal);
        }
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String s) {
        return isSecure();
    }

    @Override
    public boolean isSecure() {
        return principal != null;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
