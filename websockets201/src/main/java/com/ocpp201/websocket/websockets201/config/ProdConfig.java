package com.ocpp201.websocket.websockets201.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {


    public ProdConfig() {
        System.out.println(" PROD PROFILE ACTIVE — Using MySQL Database");
    }
}
