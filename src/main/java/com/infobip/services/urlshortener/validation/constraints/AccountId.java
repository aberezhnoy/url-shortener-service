package com.infobip.services.urlshortener.validation.constraints;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by andrew
 */
@Retention(RetentionPolicy.RUNTIME)
@NotEmpty
@NotNull
@ReportAsSingleViolation
@Constraint(validatedBy = {})
public @interface AccountId {
    String message() default "ACCOUNT_ID_ILLEGAL_VALUE";
    Class<?>[] groups() default {};
    Class<? extends Payload> []payload() default {};
}
