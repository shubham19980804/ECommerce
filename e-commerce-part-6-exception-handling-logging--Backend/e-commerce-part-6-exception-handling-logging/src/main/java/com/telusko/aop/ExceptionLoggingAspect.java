package com.telusko.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// This class is an Aspect, which allows us to add cross-cutting concerns
@Aspect
@Component
@Slf4j
public class ExceptionLoggingAspect {


    //no need to create logger instance for this class manually as we are using lombok. it will be automatically done using @SL4fj
    //  private static final Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    // Define a pointcut that targets all methods in classes annotated with @RestController
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
        // This method is empty because it's just used to define the pointcut
    }

    // This advice will run after an exception is thrown from any method matching the controllerPointcut
    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "ex")
    public void logException(Exception ex) {
        // Log the caught exception using the error level
        log.error("Exception caught: ", ex);
    }
}