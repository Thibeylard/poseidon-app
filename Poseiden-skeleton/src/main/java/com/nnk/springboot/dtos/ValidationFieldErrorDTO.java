package com.nnk.springboot.dtos;

import lombok.Data;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;

public @Data
class ValidationFieldErrorDTO {
    private String name;
    private String rejectedValue;
    private String description;

    public ValidationFieldErrorDTO(FieldError error) {
        this.name = error.getField();
        if (error.getRejectedValue() == null) {
            this.rejectedValue = "null";
        } else {
            this.rejectedValue = error.getRejectedValue().toString();
        }
        this.description = error.getDefaultMessage();
    }

    public ValidationFieldErrorDTO(ConstraintViolation<?> error) {
        this.name = error.getPropertyPath().toString();
        if (error.getInvalidValue() == null) {
            this.rejectedValue = "null";
        } else {
            this.rejectedValue = error.getInvalidValue().toString();
        }
        this.description = error.getMessage();
    }
}
