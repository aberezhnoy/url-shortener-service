package com.infobip.services.urlshortener.validation.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by andrew
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RedirectType.Validator.class)
public @interface RedirectType {
    //String message() default "com.infobip.services.urlshortener.validation.constraints.RedirectType";
    String message() default "REDIRECT_TYPE_ILLEGAL_VALUE";
    Class<?>[] groups() default {};
    Class<? extends Payload> []payload() default {};

    static class Validator implements ConstraintValidator<RedirectType, Integer> {
        @Override
        public void initialize(RedirectType redirectType) {
        }

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
            return value == null || value == 301 || value == 302;
        }
    }
}
