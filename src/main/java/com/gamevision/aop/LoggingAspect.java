package com.gamevision.aop;

import com.gamevision.model.servicemodels.GameAddServiceModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


//Interceptor
@Aspect
@Component
public class LoggingAspect {
    static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);


    //  @AfterReturning(pointcut = "execution(* com.gamevision.service.impl.GameServiceImpl.addGame(..))") //SPACE after *

    //Using @Around i/o @AfterReturning here so we can get access to ProceedingJoinPoint which we need to get the Object (its fields are used in the logged messages)
    @Around("execution(* com.gamevision.service.impl.GameServiceImpl.addGame(..))")
    public GameAddServiceModel afterAddGame(ProceedingJoinPoint pjp) throws Throwable { //JP is the addGameMethod
          GameAddServiceModel gameAdded = (GameAddServiceModel) pjp.proceed();

        LocalDateTime timeCreated = LocalDateTime.now();
        String gameTitle = gameAdded.getTitle();
        String addedByUser = gameAdded.getAddedBy();
        LOGGER.info(String.format("%s: game with title \"%s\" added by user %s", timeCreated, gameTitle, addedByUser));

        return gameAdded;
    }
}
