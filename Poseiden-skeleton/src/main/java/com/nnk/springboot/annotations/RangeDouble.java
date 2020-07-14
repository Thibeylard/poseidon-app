package com.nnk.springboot.annotations;

import com.nnk.springboot.validators.DoubleConstraintValidator;
import com.nnk.springboot.validators.PasswordConstraintValidator;
import org.apache.commons.validator.routines.DoubleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DoubleConstraintValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface RangeDouble {

    String message() default "Double is not in valid range";

    double min() default Double.NEGATIVE_INFINITY;

    double max() default Double.POSITIVE_INFINITY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}