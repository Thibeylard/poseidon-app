package com.nnk.springboot.aspects;

import com.nnk.springboot.controllers.RestController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

@Aspect
@Component
public class ControllerAspect {

    @Pointcut("execution(public * com.nnk.springboot.controllers.*ApiController.*(..))")
    private void apiRequestHttpMethods() {
    }

    @Before("apiRequestHttpMethods()")
    public void checkRequestAcceptHeader(JoinPoint joinPoint) throws HttpMediaTypeNotAcceptableException {
        RestController controller = (RestController) joinPoint.getTarget();
        String accept = controller.getRequest().getHeader("Accept");

        if (accept == null || (!accept.contains("application/json") && !accept.contains("application/*") && !accept.contains("*/*"))) {
            throw new HttpMediaTypeNotAcceptableException("API can only return application/json mediatype format.");
        }
    }
}
