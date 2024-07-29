package Kafka.Producer.Configuration;

import Kafka.Producer.Service.LoggingService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Autowired
    private LoggingService myLoggingService;

    @Before("execution(* Kafka.*.*(..))")
    public void beforeProcessing() {
        myLoggingService.initStep();
        myLoggingService.logMessage("Start");
    }

    @After("execution(* Kafka.*.*(..))")
    public void afterProcessing() {
        myLoggingService.logMessage("End");
        myLoggingService.resetStep();
    }
}
