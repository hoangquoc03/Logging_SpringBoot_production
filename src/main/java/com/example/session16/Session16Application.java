package com.example.session16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Session16Application {

    private static final Logger log = LoggerFactory.getLogger(Session16Application.class);

    public static void main(String[] args) {
        log.debug("DEBUG: Starting Session16 application");
        log.info("INFO: Starting Session16 application");
        SpringApplication.run(Session16Application.class, args);
    }
}
