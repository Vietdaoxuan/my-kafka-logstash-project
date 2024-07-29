package Kafka.Producer.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);
    private ThreadLocal<Integer> step = ThreadLocal.withInitial(() -> 0);

    public void logMessage(String message) {
        int currentStep = step.get() + 1;
        step.set(currentStep);
        logger.info("Step {}: {}", currentStep, message);
    }

    public void resetStep() {
        step.remove();
    }

    public void initStep() {
        step.set(0);
    }
}
