package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.FormErrorDTO;
import com.nnk.springboot.exceptions.ResourceIdNotFoundException;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * ApiController ResourceIdNotFoundException Handler
     *
     * @param e ResourceIdNotFoundException
     * @return Error description and Status Code
     */
    @ExceptionHandler(ResourceIdNotFoundException.class)
    public ResponseEntity<String> handleResourceIdNotFound(ResourceIdNotFoundException e) {
        return new ResponseEntity<>("Requested resource with ID " + e.getInvalidID() + " does not exist", HttpStatus.NOT_FOUND);
    }

    /**
     * ApiController HttpMediaTypeNotAcceptableException Handler
     *
     * @param e HttpMediaTypeNotAcceptableException
     * @return Error description and Status Code
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        return new ResponseEntity<>("API can only return application/json mediatype format.", HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * ApiController MethodArgumentNotValidException Handler
     *
     * @param e MethodArgumentNotValidException
     * @return Error description and Status Code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FormErrorDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<FormErrorDTO> violations = new ArrayList<>();

        for (FieldError err : errors) {
            violations.add(new FormErrorDTO(err.getField(), err.getRejectedValue(), err.getDefaultMessage()));
        }

        return new ResponseEntity<>(violations, HttpStatus.BAD_REQUEST);
    }

    /**
     * Controllers Exception Default Handler
     *
     * @param e Exception
     * @return Status Code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> defaultHandlerException(Exception e) {
        return new ResponseEntity<>("Sorry, an error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * RestApiController RepositoryConstraintViolationException Handler
     *
     * @param e RepositoryConstraintViolationException
     * @return Error description and Status Code
     */
    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public ResponseEntity<List<FormErrorDTO>> handleRepositoryConstraintViolationException(RepositoryConstraintViolationException e) {
        List<FieldError> errors = e.getErrors().getFieldErrors();
        List<FormErrorDTO> violations = new ArrayList<>();

        for (FieldError err : errors) {
            violations.add(new FormErrorDTO(err.getField(), err.getRejectedValue(), err.getDefaultMessage()));
        }

        return new ResponseEntity<>(violations, HttpStatus.BAD_REQUEST);
    }
}
