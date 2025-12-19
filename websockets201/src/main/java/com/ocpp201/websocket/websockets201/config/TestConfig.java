package com.ocpp201.websocket.websockets201.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    public TestConfig() {
        System.out.println(" TEST PROFILE ACTIVE — Using H2 In-Memory Database");
    }
}
