package com.ocpp201.websocket.websockets201.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    public DevConfig() {
        System.out.println(" DEV PROFILE ACTIVE — Using H2 Database");
    }

}
