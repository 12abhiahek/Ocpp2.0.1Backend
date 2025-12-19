//package com.ocpp201.websocket.websockets201.security;
//
//import com.ocpp201.websocket.websockets201.repository.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//@Service
//public class CustomUserDeatilsService implements UserDetailsService {
//
//    private final UserRepository userrepository;
//
//
//    public CustomUserDeatilsService(UserRepository userrepository) {
//        this.userrepository = userrepository;
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        com.ocpp201.websocket.websockets201.model.User user = userrepository.findById(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getRole().replace("ROLE_", ""))
//                .build();
//    }
//
//
//}
