package com.ocpp201.websocket.websockets201.service;

import com.ocpp201.websocket.websockets201.dto.*;
import com.ocpp201.websocket.websockets201.model.ChargingProfile;
//import com.ocpp201.websocket.websockets201.model.ChargingSchedule;
import com.ocpp201.websocket.websockets201.model.ChargingSchedulePeriod;
import com.ocpp201.websocket.websockets201.repository.ChargingProfileRepository;
import com.ocpp201.websocket.websockets201.routing.MessageRouter;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
//
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ChargingProfileService {
//
//    private final ChargingProfileRepository repo;
//    private final MessageRouter messageRouter;
//    private final ChargingProfileMapper chargingProfileMapper;
//
//    // ================= APPLY PROFILE =================
//    public void applyProfile(ChargingProfileRequest req) {
//
//        validate(req);
//
//        // -------- CREATE DB ENTITY --------
//        ChargingProfile profile = new ChargingProfile();
//
//        profile.setProfileId(
//                "TxProfile".equals(req.getChargingProfilePurpose())
//                        ? "TX-" + req.getTransactionId()
//                        : UUID.randomUUID().toString()
//        );
//
//        profile.setStackLevel(1);
//        profile.setChargingProfilePurpose(req.getChargingProfilePurpose());
//        profile.setChargingProfileKind(req.getChargingProfileKind());
//
//        ChargingSchedule schedule = new ChargingSchedule();
//        schedule.setChargingRateUnit(req.getChargingRateUnit());
//        schedule.setProfile(profile);
//
//        ChargingSchedulePeriod period = new ChargingSchedulePeriod();
//        period.setStartPeriod(0);
//        period.setLimit(req.getLimit());
//        period.setSchedule(schedule);
//
//        schedule.getPeriods().add(period);
//        profile.getSchedules().add(schedule);
//
//        repo.save(profile);
//
//        log.info(
//                "[CP][DB] Profile saved id={} purpose={} kind={} limit={}{}",
//                profile.getProfileId(),
//                profile.getChargingProfilePurpose(),
//                profile.getChargingProfileKind(),
//                req.getLimit(),
//                req.getChargingRateUnit()
//        );
//
//        // -------- SEND TO CHARGER --------
//        sendSetChargingProfile(
//                req.getChargeBoxId(),
//                req.getEvseId(),
//                profile
//        );
//    }
//
//    // ================= CLEAR PROFILE =================
//    public void clearProfile(
//            String chargeBoxId,
//            Integer evseId,
//            Long transactionId
//    ) {
//
//        String profileId = "TX-" + transactionId;
//
//        repo.findByProfileId(profileId).ifPresent(repo::delete);
//
//        Map<String, Object> payload = Map.of(
//                "chargingProfileId", profileId,
//                "evseId", evseId
//        );
//
//        WebSocketSession ws = messageRouter.getSession(chargeBoxId);
//        if (ws != null) {
//            try {
//                ws.sendMessage(new TextMessage(
//                        JsonHelper.call(
//                                JsonHelper.uid(),
//                                "ClearChargingProfile",
//                                payload
//                        )
//                ));
//                log.info(
//                        "[CP][OUT] ClearChargingProfile cp={} evse={} profile={}",
//                        chargeBoxId, evseId, profileId
//                );
//            } catch (Exception e) {
//                log.error("ClearChargingProfile failed", e);
//            }
//        }
//    }
//
////    // ================= SEND SET PROFILE =================
////    private void sendSetChargingProfile(
////            String chargeBoxId,
////            Integer evseId,
////            ChargingProfile dbProfile
////    ) {
////
////        ChargingSchedule sch = dbProfile.getSchedules().get(0);
////        ChargingSchedulePeriod p = sch.getPeriods().get(0);
////
////        //
////        // DTOs (NOT entity)
////        OcppChargingSchedulePeriod ocppPeriod =
////                new OcppChargingSchedulePeriod();
////        ocppPeriod.setStartPeriod(p.getStartPeriod());
////        ocppPeriod.setLimit(Double.valueOf(p.getLimit()));
////
////        com.ocpp201.websocket.websockets201.dto.ChargingSchedule ocppSchedule =
////                new com.ocpp201.websocket.websockets201.dto.ChargingSchedule();
////        ocppSchedule.setChargingRateUnit(sch.getChargingRateUnit());
////        ocppSchedule.setChargingSchedulePeriod(List.of(ocppPeriod));
////
////        com.ocpp201.websocket.websockets201.dto.ChargingProfile ocppProfile =
////                new com.ocpp201.websocket.websockets201.dto.ChargingProfile();
////        ocppProfile.setId(dbProfile.getProfileId());
////        ocppProfile.setStackLevel(dbProfile.getStackLevel());
////        ocppProfile.setChargingProfilePurpose(
////                dbProfile.getChargingProfilePurpose()
////        );
////        ocppProfile.setChargingProfileKind(
////                dbProfile.getChargingProfileKind()
////        );
////        ocppProfile.setChargingSchedule(ocppSchedule);
////
////        SetChargingProfileRequest req =
////                new SetChargingProfileRequest();
////        req.setEvseId(evseId);
////        req.setChargingProfile(ocppProfile);
////
////        WebSocketSession ws = messageRouter.getSession(chargeBoxId);
////        if (ws != null) {
////            try {
////                ws.sendMessage(new TextMessage(
////                        JsonHelper.call(
////                                JsonHelper.uid(),
////                                "SetChargingProfile",
////                                req
////                        )
////                ));
////
////                log.info(
////                        "[CP][OUT] SetChargingProfile cp={} evse={} limit={}{}",
////                        chargeBoxId,
////                        evseId,
////                        p.getLimit(),
////                        sch.getChargingRateUnit()
////                );
////
////            } catch (Exception e) {
////                log.error("SetChargingProfile failed", e);
////            }
////        }
////    }
//
//
//    private void sendSetChargingProfile(
//            String chargeBoxId,
//            Integer evseId,
//            ChargingProfile dbProfile
//    ) {
//
//        OcppChargingProfile ocppProfile =
//                chargingProfileMapper.toOcpp(dbProfile);
//
//        SetChargingProfileRequest req =
//                new SetChargingProfileRequest();
//        req.setEvseId(evseId);
//        req.setChargingProfile(ocppProfile);
//
//        WebSocketSession ws = messageRouter.getSession(chargeBoxId);
//        if (ws != null) {
//            try {
//                ws.sendMessage(new TextMessage(
//                        JsonHelper.call(
//                                JsonHelper.uid(),
//                                "SetChargingProfile",
//                                req
//                        )
//                ));
//            } catch (Exception e) {
//                log.error("SetChargingProfile failed", e);
//            }
//        }
//    }
//
//    // ================= VALIDATION =================
//    private void validate(ChargingProfileRequest req) {
//
//        if (req.getLimit() == null || req.getLimit() <= 0)
//            throw new IllegalArgumentException("Limit must be > 0");
//
//        if (!List.of("W", "A").contains(req.getChargingRateUnit()))
//            throw new IllegalArgumentException("Invalid chargingRateUnit");
//
//        if (!List.of("TxProfile", "ChargePointMaxProfile", "DefaultProfile")
//                .contains(req.getChargingProfilePurpose()))
//            throw new IllegalArgumentException("Invalid chargingProfilePurpose");
//
//        if (!List.of("Absolute", "Relative", "Recurring")
//                .contains(req.getChargingProfileKind()))
//            throw new IllegalArgumentException("Invalid chargingProfileKind");
//
//        if ("TxProfile".equals(req.getChargingProfilePurpose())
//                && req.getTransactionId() == null)
//            throw new IllegalArgumentException("TxProfile requires transactionId");
//    }
//}


@Service
@RequiredArgsConstructor
@Slf4j
public class ChargingProfileService {

    private final ChargingProfileRepository repository;
    private final ChargingProfileMapper mapper;
    private final MessageRouter messageRouter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ================= APPLY PROFILE =================
    @Transactional
    public void applyProfile(ChargingProfileRequest req) {

        validate(req);

        // ---------- CREATE PROFILE ----------
        ChargingProfile profile = new ChargingProfile();

        profile.setProfileId(
                "TxProfile".equals(req.getChargingProfilePurpose())
                        ? "TX-" + req.getTransactionId()
                        : UUID.randomUUID().toString()
        );

        profile.setStackLevel(1);
        profile.setChargingProfilePurpose(req.getChargingProfilePurpose());
        profile.setChargingProfileKind(req.getChargingProfileKind());
        profile.setChargingRateUnit(req.getChargingRateUnit());
        profile.setMinChargingRate(BigDecimal.ZERO);

        // ---------- CREATE PERIOD ----------
        ChargingSchedulePeriod period =
                new ChargingSchedulePeriod();
        period.setStartPeriodInSeconds(0);
        period.setStartPeriod(0);
        period.setPowerLimit(
                BigDecimal.valueOf(req.getLimit())
        );
        period.setNumberPhases(3);
        period.setProfile(profile);

        profile.getSchedulePeriods().add(period);

        repository.save(profile);

        log.info(
                "[CP][DB] Saved profileId={} purpose={} kind={} limit={}{}",
                profile.getProfileId(),
                profile.getChargingProfilePurpose(),
                profile.getChargingProfileKind(),
                req.getLimit(),
                req.getChargingRateUnit()
        );

        // ---------- SEND TO CHARGER ----------
        sendSetChargingProfile(
                req.getChargeBoxId(),
                req.getEvseId(),
                profile
        );
    }

    // ================= SEND SET PROFILE =================
    private void sendSetChargingProfile(
            String chargeBoxId,
            Integer evseId,
            ChargingProfile dbProfile
    ) {

        OcppChargingProfile ocppProfile =
                mapper.toOcpp(dbProfile);

        SetChargingProfileRequest ocppReq =
                new SetChargingProfileRequest();
        ocppReq.setEvseId(evseId);
        ocppReq.setChargingProfile(ocppProfile);

        WebSocketSession ws =
                messageRouter.getSession(chargeBoxId);

        if (ws == null) {
            log.warn("[CP] No WS session for {}", chargeBoxId);
            return;
        }

        try {
            ws.sendMessage(
                    new TextMessage(
                            JsonHelper.call(
                                    JsonHelper.uid(),
                                    "SetChargingProfile",
                                    ocppReq
                            )
                    )
            );

            log.info(
                    "[CP][OUT] SetChargingProfile cp={} evse={} limit={}{}",
                    chargeBoxId,
                    evseId,
                    ocppProfile.getSchedulePeriods()
                            .get(0)
                            .getLimit(),
                    ocppProfile.getChargingRateUnit()
            );

        } catch (Exception e) {
            log.error("SetChargingProfile failed", e);
        }
    }

    // ================= VALIDATION =================
    private void validate(ChargingProfileRequest req) {

        if (req.getLimit() == null || req.getLimit() <= 0)
            throw new IllegalArgumentException("Limit must be > 0");

        if (!List.of("W", "A").contains(req.getChargingRateUnit()))
            throw new IllegalArgumentException("Invalid chargingRateUnit");

        if (!List.of(
                        "TxProfile",
                        "ChargePointMaxProfile",
                        "DefaultProfile")
                .contains(req.getChargingProfilePurpose()))
            throw new IllegalArgumentException("Invalid chargingProfilePurpose");

        if (!List.of(
                        "Absolute",
                        "Relative",
                        "Recurring")
                .contains(req.getChargingProfileKind()))
            throw new IllegalArgumentException("Invalid chargingProfileKind");

        if ("TxProfile".equals(req.getChargingProfilePurpose())
                && req.getTransactionId() == null)
            throw new IllegalArgumentException(
                    "TxProfile requires transactionId"
            );
    }


    @Transactional
    public void clearChargingProfile(
            String chargeBoxId,
            Integer evseId,
            String chargingProfileId
    ) {

        // ---------- DELETE FROM DB ----------
        repository.findByProfileId(chargingProfileId)
                .ifPresent(profile -> {
                    repository.delete(profile);
                    log.info(
                            "[CP][DB] Deleted profileId={}",
                            chargingProfileId
                    );
                });

        // ---------- SEND TO CHARGER ----------
        ClearChargingProfileRequest ocppReq =
                new ClearChargingProfileRequest();
        ocppReq.setEvseId(evseId);
        ocppReq.setChargingProfileId(chargingProfileId);

        WebSocketSession ws =
                messageRouter.getSession(chargeBoxId);

        if (ws == null) {
            log.warn("[CP] No WS session for {}", chargeBoxId);
            return;
        }

        try {
            ws.sendMessage(
                    new TextMessage(
                            JsonHelper.call(
                                    JsonHelper.uid(),
                                    "ClearChargingProfile",
                                    ocppReq
                            )
                    )
            );

            log.info(
                    "[CP][OUT] ClearChargingProfile cp={} evse={} profileId={}",
                    chargeBoxId,
                    evseId,
                    chargingProfileId
            );

        } catch (Exception e) {
            log.error("ClearChargingProfile failed", e);
        }
    }

}

