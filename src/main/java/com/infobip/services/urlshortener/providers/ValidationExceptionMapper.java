package com.infobip.services.urlshortener.providers;

import javax.annotation.Priority;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew
 */
@Provider
@Priority(Priorities.USER)
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException ex) {
        Map<String, Object> responseBean = new HashMap<>();
        List<String> violations = new ArrayList<>();

        responseBean.put("success", false);
        responseBean.put("violations", violations);

        for (ConstraintViolation constraintViolation : ex.getConstraintViolations()) {
            violations.add(constraintViolation.getMessage());
        }

        return Response
            .status(Response.Status.PRECONDITION_FAILED)
            .type(MediaType.APPLICATION_JSON)
            .entity(responseBean)
            .build();
    }
}
