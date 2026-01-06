package com.ocpp201.websocket.websockets201.controller;

import com.ocpp201.websocket.websockets201.dto.LoginRequest;
import com.ocpp201.websocket.websockets201.dto.LoginResponse;
import com.ocpp201.websocket.websockets201.dto.RegisterRequest;
import com.ocpp201.websocket.websockets201.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }




    private final AuthService service;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {

        String token =
                service.login(req.getUsername(), req.getPassword());

        return new LoginResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {
        service.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

}
