package com.example.session16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@ConditionalOnProperty(name = "app.logging-test.enabled", havingValue = "true")
public class LoggingTestController {

    private static final Logger log = LoggerFactory.getLogger(LoggingTestController.class);

    @GetMapping("/test-log")
    public String testLog() {
        log.info("Test INFO log event");
        log.warn("Test WARN log event");
        log.error("Test ERROR log event");
        return "Test log events written";
    }

    @GetMapping("/test-exception")
    public String testException() {
        try {
            throw new RuntimeException("This is a test exception");
        } catch (RuntimeException exception) {
            log.error("Exception occurred during logging test", exception);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Test exception",
                    exception
            );
        }
    }
}
