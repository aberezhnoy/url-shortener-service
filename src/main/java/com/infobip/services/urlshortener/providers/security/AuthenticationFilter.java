package com.infobip.services.urlshortener.providers.security;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.List;

/**
 * Created by andrew
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Inject
    private AuthenticationProvider authenticationProvider;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        BasicAuthentication auth = getAuthentication(containerRequestContext);

        if (auth == null) {
            throw new UnauthenticatedException("Authentication not provided");
        }

        if (!authenticationProvider.isAuthenticationValid(auth.principal, auth.credentials)) {
            throw new UnauthenticatedException("Authentication not valid");
        }

        SecurityContext securityContext = new GenericSecurityContext(auth.principal);
        containerRequestContext.setSecurityContext(securityContext);
    }

    private BasicAuthentication getAuthentication(ContainerRequestContext containerRequestContext) {
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
        List<String> authorization = headers.get(AUTHORIZATION_HEADER);

        if (authorization == null || authorization.isEmpty()) {
            return null;
        }

        String base64Auth = authorization.get(0).replaceFirst("[B|b]asic ", "").trim();
        byte[] authSource = DatatypeConverter.parseBase64Binary(base64Auth);

        if (authSource == null || authSource.length == 0) {
            return null;
        }

        String authFields[] =  new String(authSource).split(":", 2);

        BasicAuthentication auth = new BasicAuthentication();

        if (authFields.length != 2) {
            return null;
        }

        auth.principal = authFields[0];
        auth.credentials = authFields[1];

        return auth;
    }

    private static class BasicAuthentication {
        String principal;
        String credentials;
    }
}
