package com.btvn.medicore.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut(
            "execution(* com.btvn.medicore.controller..*(..))"
    )
    public void controllerLayer() {}

    @Pointcut(
            "execution(* com.btvn.medicore.service..*(..))"
    )
    public void serviceLayer() {}

    @Before(
            "controllerLayer() || serviceLayer()"
    )
    public void before(
            JoinPoint joinPoint
    ) {

        log.info(
                "ENTER {}",
                joinPoint.getSignature()
        );
    }

    @AfterReturning(
            value =
                    "controllerLayer() || serviceLayer()",
            returning = "result"
    )
    public void success(
            JoinPoint joinPoint,
            Object result
    ) {

        log.info(
                "SUCCESS {}",
                joinPoint.getSignature()
        );
    }

    @AfterThrowing(
            value =
                    "controllerLayer() || serviceLayer()",
            throwing = "ex"
    )
    public void error(
            JoinPoint joinPoint,
            Exception ex
    ) {

        log.error(
                "ERROR {} : {}",
                joinPoint.getSignature(),
                ex.getMessage()
        );
    }
}