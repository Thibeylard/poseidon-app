package com.nnk.springboot.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controllers.RestController;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.HashMap;

@Aspect
@Component
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Autowired
    public LoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("execution(public * com.nnk.springboot.controllers.*Controller.*(..))")
    public void requestRestMethods() {
    }

    @Before("requestRestMethods()")
    public void logRestRequest(JoinPoint joinPoint) {
        RestController controller = (RestController) joinPoint.getTarget();
        String method = controller.getRequest().getMethod();
        String uri = controller.getRequest().getRequestURI();
        String paramJson;
        try {
            HashMap<String, String[]> params = new HashMap<>(controller.getRequest().getParameterMap());
            params.keySet().removeIf(key -> key.equals("_csrf"));
            String body = IOUtils.toString(controller.getRequest().getReader());
            paramJson = objectMapper.writeValueAsString(params);

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


    @AfterReturning(value = "requestRestMethods()", returning = "response")
    public void logRestReturns(ResponseEntity<?> response) {
        try {
            Logger.info("Request succeeded and returned {}", objectMapper.writeValueAsString(response.getBody()));
        } catch (JsonProcessingException e) {
            Logger.info("Request succeeded");
        }
    }

    @AfterThrowing(value = "requestRestMethods()", throwing = "e")
    public void logRestExceptions(Exception e) throws Exception {
        Logger.error("Request failed on {} with : {}", e.getClass().getCanonicalName(), e.getMessage());
        throw e;
    }

    @Pointcut("execution( * com.nnk.springboot.controllers.GlobalControllerAdvice.*(..))")
    public void withinGlobalControllerAdvice() {
    }

    @Before("withinGlobalControllerAdvice() && execution(* handleHttpMediaTypeNotAcceptableException(..))()")
    public void logAcceptMediaRestExceptions(JoinPoint joinPoint) throws Exception {
        Exception e = (Exception) joinPoint.getArgs()[0];
        Logger.error("Request aborted on {} with : {}", e.getClass().getCanonicalName(), e.getMessage());
    }

    @Before("withinGlobalControllerAdvice() && execution(* handleMethodArgumentNotValidException(..))")
    public void logValidationRestException(JoinPoint joinPoint) throws Exception {
        MethodArgumentNotValidException e = (MethodArgumentNotValidException) joinPoint.getArgs()[0];
        Logger.error("Request aborted on invalid arguments : {} ", e.getBindingResult().getFieldErrors());
    }
}
