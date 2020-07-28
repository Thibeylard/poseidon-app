package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.ErrorDTO;
import com.nnk.springboot.dtos.ValidationErrorDTO;
import com.nnk.springboot.dtos.ValidationFieldErrorDTO;
import com.nnk.springboot.exceptions.ResourceIdNotFoundException;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * ResourceNotFoundException and ResourceIdNotFoundException common Handler
     *
     * @param e ResourceNotFoundException or ResourceIdNotFoundException
     * @return Error description and Status Code
     */
    @ExceptionHandler({ResourceNotFoundException.class, ResourceIdNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleResourceNotFound(Exception e) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    /**
     * ApiController HttpMediaTypeNotAcceptableException Handler
     *
     * @param e HttpMediaTypeNotAcceptableException
     * @return Error description and Status Code
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorDTO> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.name(),
                "API can only return application/json mediatype format.");
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }


    /**
     * Controllers Exception Default Handler
     *
     * @param e Exception
     * @return Status Code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> defaultHandlerException(Exception e) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Sorry, an error occurred.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * ConstraintViolationException Handler
     *
     * @param e ConstraintViolationException
     * @return Error description and Status Code
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorDTO> handleConstraintViolationException(ConstraintViolationException e) {
        List<ValidationFieldErrorDTO> fieldErrors = new ArrayList<>();

        for (ConstraintViolation<?> err : e.getConstraintViolations())
            fieldErrors.add(new ValidationFieldErrorDTO(err));

        return new ResponseEntity<>(getValidationErrorDTO(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    /**
     * MethodArgumentNotValidException Handler
     *
     * @param e MethodArgumentNotValidException
     * @return Error description and Status Code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationFieldErrorDTO> fieldErrors = new ArrayList<>();

        for (FieldError err : e.getBindingResult().getFieldErrors())
            fieldErrors.add(new ValidationFieldErrorDTO(err));

        return new ResponseEntity<>(getValidationErrorDTO(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    /**
     * RepositoryConstraintViolationException Handler
     *
     * @param e RepositoryConstraintViolationException
     * @return Error description and Status Code
     */
    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public ResponseEntity<ValidationErrorDTO> handleRepositoryConstraintViolationException(RepositoryConstraintViolationException e) {
        List<ValidationFieldErrorDTO> fieldErrors = new ArrayList<>();

        for (FieldError err : e.getErrors().getFieldErrors())
            fieldErrors.add(new ValidationFieldErrorDTO(err));

        return new ResponseEntity<>(getValidationErrorDTO(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Return ValidationErrorDTO based on FieldError List
     *
     * @param errors List of FieldError
     * @return Error description as ValidationErrorDTO
     */
    private ValidationErrorDTO getValidationErrorDTO(List<ValidationFieldErrorDTO> errors) {

        return new ValidationErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                "Object has invalid values",
                errors);
    }

}
