package com.nnk.springboot.dtos;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

public @Data
class ErrorDTO {
    private Timestamp timestamp;
    private int statusCode;
    private String statusDescription;
    private String description;


    public ErrorDTO(int statusCode, String statusDescription, String description) {
        this.timestamp = Timestamp.from(Instant.now());
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.description = description;
    }
}
