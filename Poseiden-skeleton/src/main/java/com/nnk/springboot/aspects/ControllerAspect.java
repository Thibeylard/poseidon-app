package com.nnk.springboot.aspects;

import com.nnk.springboot.controllers.RestController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

@Aspect
@Component
public class ControllerAspect {

    @Before("execution(* com.nnk.springboot.controllers.*Controller.*(*))")
    public void checkRequestAcceptHeader(JoinPoint joinPoint) throws HttpMediaTypeNotAcceptableException {
        RestController controller = (RestController) joinPoint.getTarget();
        String accept = controller.getRequest().getHeader("Accept");

        if (accept == null || !accept.contains("application/json")) {
            throw new HttpMediaTypeNotAcceptableException("API can only return application/json mediatype format.");
        }
    }

    //TODO Logging before controller execution method

    //TODO Logging after execution method with two scenarios : Exception and Returning
}
