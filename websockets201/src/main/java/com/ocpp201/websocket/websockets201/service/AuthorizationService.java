package com.ocpp201.websocket.websockets201.service;
//
//import com.ocpp201.websocket.websockets201.model.IdTag;
//import com.ocpp201.websocket.websockets201.repository.IdTagRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class AuthorizationService {
//
//    private final IdTagRepository idTagRepository;
//
//    public AuthorizationService(IdTagRepository idTagRepository) {
//        this.idTagRepository = idTagRepository;
//    }
//
//    public String validateToken(String idToken) {
//
//        Optional<IdTag> optional = idTagRepository.findByIdToken(idToken);
//
//        if (optional.isEmpty()) {
//            return "Invalid";
//        }
//
//        IdTag tag = optional.get();
//
//        if (tag.getStatus().equalsIgnoreCase("Blocked")) {
//            return "Blocked";
//        }
//
//        if (tag.getExpiryDate() != null &&
//                tag.getExpiryDate().isBefore(LocalDateTime.now())) {
//            return "Expired";
//        }
//
//        return "Accepted";
//    }
//
//    // Auto-register new tags if needed
//    public IdTag registerNewToken(String idToken) {
//        IdTag tag = new IdTag();
//        tag.setIdToken(idToken);
//        tag.setStatus("Accepted");
//        return idTagRepository.save(tag);
//    }
//
//    public IdTag updateExpiryDate(String idToken, LocalDateTime expiryDate) {
//        Optional<IdTag> optional = idTagRepository.findByIdToken(idToken);
//        if (optional.isPresent()) {
//            IdTag tag = optional.get();
//            tag.setExpiryDate(expiryDate);
//            return idTagRepository.save(tag);
//        }
//        throw new IllegalArgumentException("IdToken not found: " + idToken);
//    }
//
//
//
//
//
//
////    private final IdTagRepository  IdTagRepository;
////
////    public AuthorizationService(com.ocpp201.websocket.websockets201.repository.IdTagRepository idTagRepository) {
////        IdTagRepository = idTagRepository;
////    }
////
////    public String validateToken(String idTag) {
////
////        Optional<OcppTag> optional = IdTagRepository.findByIdTag(idTag);
////
////        if (optional.isEmpty()) {
////            return "Invalid";
////        }
////
////        OcppTag tag = optional.get();
////
////        // Blocked / inactive
////        if (Boolean.FALSE.equals(tag.getActive())) {
////            return "Blocked";
////        }
////
////        // Expired
////        if (tag.getExpiryDate() != null &&
////                tag.getExpiryDate().isBefore(Instant.now())) {
////            return "Expired";
////        }
////
////        return "Accepted";
////    }
////
////    /**
////     * Optional auto-registration
////     */
////    public OcppTag registerNewToken(String idToken) {
////
////        OcppTag tag = new OcppTag();
////        tag.setIdTag(idToken);
////        tag.setActive(true);
////        tag.setTagType("RFID");
////        tag.setCreatedAt(Instant.now());
////
////        return IdTagRepository.save(tag);
////    }
//}


import com.ocpp201.websocket.websockets201.model.IdTag;
import com.ocpp201.websocket.websockets201.repository.IdTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final IdTagRepository ocppTagRepository;

    public String validateToken(String idToken) {

        Optional<IdTag> optional =
                ocppTagRepository.findByIdTag(idToken);

        if (optional.isEmpty()) {
            return "Invalid";
        }

        IdTag tag = optional.get();

        // Explicit status-based validation
        if ("Blocked".equalsIgnoreCase(tag.getStatus())) {
            return "Blocked";
        }

        if ("Expired".equalsIgnoreCase(tag.getStatus())) {
            return "Expired";
        }

        if (tag.getExpiryDate() != null &&
                tag.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Expired";
        }

        return "Accepted";
    }

    // Auto-register new tag
    public IdTag registerNewToken(String idToken) {

        IdTag tag = new IdTag();
        tag.setIdTag(idToken);
        tag.setStatus("Accepted");
        tag.setTagType("RFID");
        tag.setCreateTime(LocalDateTime.now());

        return ocppTagRepository.save(tag);

    }

    public IdTag updateStatus(String idToken, String status) {

        IdTag tag = ocppTagRepository.findByIdTag(idToken)
                .orElseThrow(() ->
                        new IllegalArgumentException("IdTag not found: " + idToken));

        tag.setStatus(status);
        tag.setModifyTime(LocalDateTime.now());
        return ocppTagRepository.save(tag);
    }

    public IdTag updateExpiryDate(String idToken, LocalDateTime expiryDate) {

        IdTag tag = ocppTagRepository.findByIdTag(idToken)
                .orElseThrow(() ->
                        new IllegalArgumentException("IdTag not found: " + idToken));

        tag.setExpiryDate(expiryDate);
        tag.setModifyTime(LocalDateTime.now());
        return ocppTagRepository.save(tag);
    }
}
