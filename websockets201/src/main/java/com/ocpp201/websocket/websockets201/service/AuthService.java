package com.ocpp201.websocket.websockets201.service;

import com.ocpp201.websocket.websockets201.dto.RegisterRequest;
import com.ocpp201.websocket.websockets201.jwt.JwtTokenProvider;
import com.ocpp201.websocket.websockets201.model.User;
import com.ocpp201.websocket.websockets201.repository.UserRepository;
import com.ocpp201.websocket.websockets201.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider provider;


    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole(Role.valueOf("ROLE_" + request.getRole()));
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        user.setActive(true);

        userRepository.save(user);
    }

    public String login(String username, String password) {

        Authentication auth =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username, password));

        return provider.generateToken((UserDetails) auth.getPrincipal());
    }
}
