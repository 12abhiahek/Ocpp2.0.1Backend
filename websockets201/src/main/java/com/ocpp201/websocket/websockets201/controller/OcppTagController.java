package com.ocpp201.websocket.websockets201.controller;
//
//import com.ocpp201.websocket.websockets201.service.AuthorizationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/idtags")
//@RequiredArgsConstructor
//public class IdTagController {
//
//    private final AuthorizationService authorizationService;
//    @PostMapping("/idtag/expiry")
//    public ResponseEntity<?>setExpiryDate(
//            @RequestParam String idToken,
//            @RequestParam String expiryDateStr){
//        // Parse expiryDateStr to LocalDateTime
//        try {
//            var expiryDate = java.time.LocalDateTime.parse(expiryDateStr);
//            var updatedTag = authorizationService.updateExpiryDate(idToken, expiryDate);
//            return ResponseEntity.ok(updatedTag);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid date format or IdToken not found");
//        }
//    }
//}


import com.ocpp201.websocket.websockets201.dto.OcppTagRequest;
import com.ocpp201.websocket.websockets201.dto.UpdateStatusRequest;
import com.ocpp201.websocket.websockets201.model.IdTag;
import com.ocpp201.websocket.websockets201.repository.IdTagRepository;
import com.ocpp201.websocket.websockets201.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ocpp-tags")
@RequiredArgsConstructor
@Slf4j
public class OcppTagController {

    private final IdTagRepository ocppTagRepository;
    private final AuthorizationService authorizationService;

    // ------------------------------------------------
    // GET all OCPP tags
    // ------------------------------------------------
    @GetMapping
    public List<IdTag> getAllTags() {
        return ocppTagRepository.findAll();
    }

    // ------------------------------------------------
    // GET tag by idTag
    // ------------------------------------------------
    @GetMapping("/{idTag}")
    public ResponseEntity<IdTag> getByIdTag(@PathVariable String idTag) {
        return ocppTagRepository.findByIdTag(idTag)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------------------------
    // CREATE / REGISTER new tag
    // ------------------------------------------------
    @PostMapping
    public ResponseEntity<IdTag> createTag(
            @RequestBody OcppTagRequest request) {

        if (ocppTagRepository.findByIdTag(request.getIdTag()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        IdTag tag = new IdTag();
        tag.setIdTag(request.getIdTag());
        tag.setStatus(
                request.getStatus() != null ? request.getStatus() : "Accepted"
        );
        tag.setTagType(
                request.getTagType() != null ? request.getTagType() : "RFID"
        );
        tag.setExpiryDate(request.getExpiryDate());
        tag.setCreateTime(LocalDateTime.now());

        IdTag saved = ocppTagRepository.save(tag);

        log.info("OCPP tag created: {}", saved.getIdTag());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ------------------------------------------------
    // UPDATE status (Block / Unblock / Expire)
    // ------------------------------------------------
    @PatchMapping("/{idTag}/status")
    public ResponseEntity<IdTag> updateStatus(
            @PathVariable String idTag,
            @RequestBody UpdateStatusRequest request) {

        IdTag updated =
                authorizationService.updateStatus(idTag, request.getStatus());

        return ResponseEntity.ok(updated);
    }

    // ------------------------------------------------
    // UPDATE expiry date
    // ------------------------------------------------
    @PatchMapping("/{idTag}/expiry")
    public ResponseEntity<IdTag> updateExpiry(
            @PathVariable String idTag,
            @RequestBody LocalDateTime expiryDate) {

        IdTag updated =
                authorizationService.updateExpiryDate(idTag, expiryDate);

        return ResponseEntity.ok(updated);
    }

    // ------------------------------------------------
    // DELETE tag (optional – admin use)
    // ------------------------------------------------
    @DeleteMapping("/{idTag}")
    public ResponseEntity<Void> deleteTag(@PathVariable String idTag) {

        IdTag tag = ocppTagRepository.findByIdTag(idTag)
                .orElseThrow(() ->
                        new IllegalArgumentException("Tag not found: " + idTag));

        ocppTagRepository.delete(tag);
        return ResponseEntity.noContent().build();
    }
}
