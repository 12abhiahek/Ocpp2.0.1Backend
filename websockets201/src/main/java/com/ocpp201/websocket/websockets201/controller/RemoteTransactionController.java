package com.ocpp201.websocket.websockets201.controller;

import com.ocpp201.websocket.websockets201.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/remote")
@RequiredArgsConstructor
public class RemoteTransactionController {

    private final CommandService commandService;
//
//    @PostMapping("/start")
//    public ResponseEntity<?> remoteStart(
//            @RequestParam String cpId,
//            @RequestParam String idToken) {
//        try {
//            commandService.sendRemoteStart(cpId, idToken);
//            return ResponseEntity.ok("RemoteStartTransaction sent to " + cpId);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/stop")
//    public ResponseEntity<?> remoteStop(
//            @RequestParam String cpId,
//            @RequestParam String transactionId) {
//        try {
//            commandService.sendRemoteStop(cpId, transactionId);
//            return ResponseEntity.ok("RemoteStopTransaction sent to " + cpId);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }




    @PostMapping("/start")
    public ResponseEntity<?> remoteStart(
            @RequestParam("cpId") String cpId,
            @RequestParam("idToken") String idToken) {
        try {
            commandService.sendRemoteStart(cpId, idToken);
            return ResponseEntity.ok("RemoteStart sent to " + cpId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<?> remoteStop(
            @RequestParam("cpId") String cpId,
            @RequestParam("transactionId") Long transactionId) {
        try {
            commandService.sendRemoteStop(cpId, transactionId);
            return ResponseEntity.ok("RemoteStop sent for txId=" + transactionId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
