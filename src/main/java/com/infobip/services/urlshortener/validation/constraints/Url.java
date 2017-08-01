package com.infobip.services.urlshortener.validation.constraints;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by andrew
 */
@Retention(RetentionPolicy.RUNTIME)
@NotEmpty
@URL
@ReportAsSingleViolation
@Constraint(validatedBy = {})
public @interface Url {
    String message() default "URL_ILLEGAL_VALUE";
    Class<?>[] groups() default {};
    Class<? extends Payload> []payload() default {};
}
