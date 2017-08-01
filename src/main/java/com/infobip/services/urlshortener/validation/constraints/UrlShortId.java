package com.infobip.services.urlshortener.validation.constraints;

import javax.validation.*;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by andrew
 */
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "[a-zA-Z0-9]+")
@ReportAsSingleViolation
@Constraint(validatedBy = {})
public @interface UrlShortId {
    String message() default "URL_ID_ILLEGAL_VALUE";
    Class<?>[] groups() default {};
    Class<? extends Payload> []payload() default {};
}
