package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String username;

    private String password;

    private String role; // ROLE_ADMIN, ROLE_OPERATOR


}
