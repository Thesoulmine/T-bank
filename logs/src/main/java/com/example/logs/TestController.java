package com.example.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {

    private static final Logger log = LogManager.getLogger(TestController.class);

    @GetMapping("/test")
    public String test() {
        UUID uuid = UUID.randomUUID();
        MDC.put("requestId", uuid.toString());
        
        log.info("Test request started");

        return uuid.toString();
    }
}
