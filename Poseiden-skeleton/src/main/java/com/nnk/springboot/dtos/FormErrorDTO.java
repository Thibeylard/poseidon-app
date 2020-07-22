package com.nnk.springboot.dtos;

import lombok.Data;
import org.springframework.lang.Nullable;

public @Data
class FormErrorDTO {
    private String field;
    private String rejectedValue;
    private String description;

    public FormErrorDTO(String field, @Nullable Object rejectedValue, String description) {
        this.field = field;

        if (rejectedValue == null) {
            this.rejectedValue = "null";
        } else {
            this.rejectedValue = rejectedValue.toString();
        }

        this.description = description;
    }
}
