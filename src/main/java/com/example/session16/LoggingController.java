package com.example.session16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoggingController {

    private static final Logger log = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping("/")
    public Map<String, String> home() {
        log.trace("TRACE: Home endpoint was called");
        log.debug("DEBUG: Processing home endpoint");
        log.info("INFO: Home endpoint was called");
        log.warn("WARN: Demonstration warning log");
        log.error("ERROR: Demonstration error log");
        return Map.of(
                "application", "Session16",
                "message", "Spring Boot profile-specific logging is running"
        );
    }
}
