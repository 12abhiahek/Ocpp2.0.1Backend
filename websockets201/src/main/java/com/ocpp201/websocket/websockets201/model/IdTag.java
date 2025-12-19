package com.ocpp201.websocket.websockets201.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "id_tags")
//public class IdTag {
//
// @Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
//private Long id;
//
//@Column(unique = true, nullable = false)
//private String idToken;     // RFID / ISO15118 Token
//
//@Column(nullable = false)
//private String status;      // Accepted, Blocked, Expired, Invalid
//
//private LocalDateTime expiryDate;
//
//private LocalDateTime createdAt = LocalDateTime.now();
//}



@Entity
@Table(name = "ocpp_tag")
@Getter
@Setter
@NoArgsConstructor
public class IdTag {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "ocpp_tag_pk")
 private Long id;

 @Column(name = "id_tag", unique = true, nullable = false)
 private String idTag;   // RFID / ISO15118

 @Column(name = "status", nullable = false)
 private String status;  // Accepted, Blocked, Expired, Invalid

 @Column(name = "expiry_date")
 private LocalDateTime expiryDate;

 @Column(name = "tag_type")
 private String tagType; // RFID / APP / ISO15118

 @Column(name = "create_time")
 private LocalDateTime createTime = LocalDateTime.now();

 @Column(name = "modify_time")
 private LocalDateTime modifyTime;
}


