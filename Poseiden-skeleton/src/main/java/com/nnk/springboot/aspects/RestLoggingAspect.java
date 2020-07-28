package com.nnk.springboot.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controllers.RestController;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.HashMap;

@Aspect
@Component
public class RestLoggingAspect {

    private final ObjectMapper objectMapper;
    private String lastRequestBody;

    @Autowired
    public RestLoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("execution(public * com.nnk.springboot.controllers.*ApiController.*(..))")
    private void apiRequestHttpMethods() {
    }

    @Before("apiRequestHttpMethods()")
    public void logRestRequest(JoinPoint joinPoint) {
        RestController controller = (RestController) joinPoint.getTarget();
        String method = controller.getRequest().getMethod();
        String uri = controller.getRequest().getRequestURI();
        String paramJson;
        try {
            HashMap<String, String[]> params = new HashMap<>(controller.getRequest().getParameterMap());
            params.keySet().removeIf(key -> key.equals("_csrf"));
            paramJson = objectMapper.writeValueAsString(params);

            String body = "";
            try {
                body = IOUtils.toString(controller.getRequest().getReader());
            } catch (IllegalStateException e) {
                Logger.debug("Body request already been read.");
            }

            if (!params.isEmpty() && !body.isEmpty()) {
                Logger.info("{} request on {} with parameters : {} and body {}", method, uri, paramJson, body);
            } else if (params.isEmpty() && !body.isEmpty()) {
                Logger.info("{} request on {} with body {}", method, uri, body);
            } else if (!params.isEmpty()) {
                Logger.info("{} request on {} with params {}", method, uri, paramJson);
            } else {
                Logger.info("{} request on {}", method, uri);
            }

        } catch (IOException e) {
            Logger.error(e.getMessage());
            Logger.info("{} request on {} with unknown parameters", method, uri);
        }

    }


    @AfterReturning(value = "apiRequestHttpMethods()", returning = "response")
    public void logRestReturns(ResponseEntity<?> response) {
        try {
            Logger.info("Request succeeded and returned {}", objectMapper.writeValueAsString(response.getBody()));
        } catch (JsonProcessingException e) {
            Logger.info("Request succeeded");
        }
    }

    /*@AfterThrowing(value = "apiRequestHttpMethods()", throwing = "e")
    public void logRestExceptions(Exception e) throws Exception {
        Logger.error("Request failed on {} with : {}", e.getClass().getCanonicalName(), e.getMessage());
        throw e;
    }*/

    @Pointcut("execution( * com.nnk.springboot.controllers.GlobalControllerAdvice.*(..))")
    private void withinGlobalControllerAdvice() {
    }

    @Pointcut("execution(public * *(org.springframework.web.bind.MethodArgumentNotValidException))")
    private void isMethodArgumentNotValidExceptionHandler() {
    }

    @Pointcut("execution(public * *(org.springframework.data.rest.core.RepositoryConstraintViolationException))")
    private void isRepositoryConstraintViolationExceptionHandler() {
    }


    @Before("withinGlobalControllerAdvice() && execution(* *(..)) && !isMethodArgumentNotValidExceptionHandler() && !isRepositoryConstraintViolationExceptionHandler()")
    public void logGlobalControllerAdvice(JoinPoint joinPoint) {
        Exception e = (Exception) joinPoint.getArgs()[0];
        Logger.error("Request aborted on {} with : {}", e.getClass().getCanonicalName(), e.getMessage());
    }

    @Before("withinGlobalControllerAdvice() && isMethodArgumentNotValidExceptionHandler()")
    public void logGlobalControllerAdviceValidationRestException(JoinPoint joinPoint) {
        MethodArgumentNotValidException e = (MethodArgumentNotValidException) joinPoint.getArgs()[0];
        Logger.error("Request aborted on invalid values : {} ", e.getBindingResult().getFieldErrors());

    }

    @Before("withinGlobalControllerAdvice() && isRepositoryConstraintViolationExceptionHandler()")
    public void logGlobalControllerAdviceRepositoryValidationRestException(JoinPoint joinPoint) {
        RepositoryConstraintViolationException e = (RepositoryConstraintViolationException) joinPoint.getArgs()[0];
        Logger.error("Request aborted on invalid values : {} ", e.getErrors().getFieldErrors());
    }
}
