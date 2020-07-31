package com.nnk.springboot.dtos;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public @Data
class ValidationErrorDTO {
    private Timestamp timestamp;
    private int statusCode;
    private String statusDescription;
    private String description;
    private List<ValidationFieldErrorDTO> invalidFields;


    public ValidationErrorDTO(int statusCode, String statusDescription, String description, List<ValidationFieldErrorDTO> invalidFields) {
        this.timestamp = Timestamp.from(Instant.now());
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.description = description;
        this.invalidFields = invalidFields;
    }
}
