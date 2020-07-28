package com.nnk.springboot.dtos;

import lombok.Data;
import org.springframework.lang.Nullable;

public @Data
class ValidationFieldErrorDTO {
    private String name;
    private String rejectedValue;
    private String description;

    public ValidationFieldErrorDTO(String name, @Nullable Object rejectedValue, String description) {
        this.name = name;
        if (rejectedValue == null) {
            this.rejectedValue = "null";
        } else {
            this.rejectedValue = rejectedValue.toString();
        }
        this.description = description;
    }
}
