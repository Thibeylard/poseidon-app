package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.FormErrorDTO;
import com.nnk.springboot.exceptions.ResourceIdNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceIdNotFoundException.class)
    public ResponseEntity<String> handleResourceIdNotFound(ResourceIdNotFoundException e) {
        Logger.error(e.getMessage());
        return new ResponseEntity<>("Requested resource with ID " + e.getInvalidID() + "does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
        Logger.error(e.getMessage());
        return new ResponseEntity<>("Sorry, an error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FormErrorDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<FormErrorDTO> violations = new ArrayList<>();

        for (FieldError err : errors) {
            violations.add(new FormErrorDTO(err.getField(), err.getRejectedValue(), err.getDefaultMessage()));
        }

        return new ResponseEntity<>(violations, HttpStatus.BAD_REQUEST);
    }
}
