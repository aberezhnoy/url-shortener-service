package com.infobip.services.urlshortener.providers;

import com.infobip.services.urlshortener.services.impl.BusinessException;
import com.infobip.services.urlshortener.providers.security.UnauthenticatedException;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Map<Class, Response.Status> exceptionMappings = new HashMap<>();

    static {
        exceptionMappings.put(NotFoundException.class, Response.Status.NOT_FOUND);
        exceptionMappings.put(ForbiddenException.class, Response.Status.FORBIDDEN);
        exceptionMappings.put(UnauthenticatedException.class, Response.Status.UNAUTHORIZED);
    }

    @Override
    public Response toResponse(Throwable ex) {
        Map<String, Object> responseBean = new HashMap<>();
        responseBean.put("success", false);
        responseBean.put("description", ex.getMessage());

        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        if (exceptionMappings.containsKey(ex.getClass())) {
            status = exceptionMappings.get(ex.getClass());
        } else if (ex instanceof BusinessException) {
            int statusCode = ((BusinessException) ex).getStatusCode();
            status = Response.Status.fromStatusCode(statusCode);
        }

        return Response
            .status(status)
            .type(MediaType.APPLICATION_JSON)
            .entity(responseBean)
            .build();
    }
}
