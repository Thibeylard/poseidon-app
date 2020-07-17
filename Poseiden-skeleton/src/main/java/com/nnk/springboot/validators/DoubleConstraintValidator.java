package com.nnk.springboot.validators;

import com.nnk.springboot.annotations.RangeDouble;
import org.apache.commons.validator.routines.DoubleValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleConstraintValidator implements ConstraintValidator<RangeDouble, Double> {

    private double minValue;
    private double maxValue;

    @Override
    public void initialize(RangeDouble constraintAnnotation) {
        this.minValue = constraintAnnotation.min();
        this.maxValue = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        if (aDouble == null) {
            return false;
        }
        DoubleValidator doubleValidator = new DoubleValidator();
        return doubleValidator.isInRange(aDouble, minValue, maxValue);
    }
}
