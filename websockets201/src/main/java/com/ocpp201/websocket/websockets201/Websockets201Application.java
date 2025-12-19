package com.ocpp201.websocket.websockets201;

import com.ocpp201.websocket.websockets201.config.OpenLogOnStart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class Websockets201Application {

	public static void main(String[] args) {
		SpringApplication.run(Websockets201Application.class, args);
	}

	@Bean
	public OpenLogOnStart openLogOnStart() {
		return new OpenLogOnStart();
	}

}
