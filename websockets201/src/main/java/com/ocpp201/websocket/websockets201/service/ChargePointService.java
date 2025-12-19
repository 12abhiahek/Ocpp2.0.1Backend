package com.ocpp201.websocket.websockets201.service;


import com.ocpp201.websocket.websockets201.model.ChargePoint;
import com.ocpp201.websocket.websockets201.repository.ChargePointRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ChargePointService {

 //   private final Map<String, ChargePoint> cps = new ConcurrentHashMap<>();

//    public ChargePoint registerOrUpdate(String id, String vendor, String model) {
//        ChargePoint cp = cps.getOrDefault(id, new ChargePoint());
//        cp.setId(id);
//        cp.setVendor(vendor);
//        cp.setModel(model);
//        cp.setLastHeartbeat(Instant.now());
//        cps.put(id, cp);
//        return cp;
//    }
//
//    public void updateStatus(String id, String status) {
//        cps.computeIfPresent(id, (k, cp) -> {
//            cp.setStatus(status);
//            cp.setLastHeartbeat(Instant.now());
//            return cp;
//        });
//    }
//
//    public void heartbeat(String id) {
//        cps.computeIfPresent(id, (k, cp) -> {
//            cp.setLastHeartbeat(Instant.now());
//            return cp;
//        });
//    }
//
//    public Map<String, ChargePoint> getAll() {
//        return cps;
//    }




//    private final ChargePointRepository repository;
//
//    public ChargePointService(ChargePointRepository repository) {
//        this.repository = repository;
//    }
//
//    public ChargePoint registerOrUpdate(String id, String vendor, String model) {
//        ChargePoint cp = repository.findById(id).orElse(new ChargePoint());
//        cp.setId(id);
//        cp.setVendor(vendor);
//        cp.setModel(model);
//        cp.setLastHeartbeat(Instant.now());
//        return repository.save(cp);
//    }
//
//    public void updateStatus(String id, String status) {
//        repository.findById(id).ifPresent(cp -> {
//            cp.setStatus(status);
//            cp.setLastHeartbeat(Instant.now());
//            repository.save(cp);
//        });
//    }
//
//    public void heartbeat(String id) {
//        repository.findById(id).ifPresent(cp -> {
//            cp.setLastHeartbeat(Instant.now());
//            repository.save(cp);
//        });
//    }
//
//    public Map<String, ChargePoint> getAll() {
//        return repository.findAll().stream()
//                .collect(Collectors.toMap(ChargePoint::getId, cp -> cp));
//    }








    private final ChargePointRepository chargePointRepository;

    public ChargePointService(ChargePointRepository chargePointRepository) {
        this.chargePointRepository = chargePointRepository;
    }

    // ------------------------------------------------
    // BootNotification → register or update
    // ------------------------------------------------
    public ChargePoint registerOrUpdate(
            String chargeBoxId,
            String vendor,
            String model
    ) {

        ChargePoint cb = chargePointRepository.findByChargeBoxId(chargeBoxId)
                .orElseGet(ChargePoint::new);

        cb.setChargeBoxId(chargeBoxId);
        cb.setVendor(vendor);
        cb.setModel(model);
//        cb.setEndpointAddress(endpointAddress);
//        cb.setOcppProtocol(protocol);
        cb.setLastHeartbeat(Instant.now());
        cb.setRegistrationStatus("Accepted");

        return chargePointRepository.save(cb);
    }

    // ------------------------------------------------
    // StatusNotification
    // ------------------------------------------------
    public void updateStatus(String chargeBoxId, String status) {
        chargePointRepository.findByChargeBoxId(chargeBoxId).ifPresent(cb -> {
            cb.setStatus(status);
            cb.setLastHeartbeat(Instant.now());
            chargePointRepository.save(cb);
        });
    }

    // ------------------------------------------------
    // Heartbeat
    // ------------------------------------------------
    public void heartbeat(String chargeBoxId) {
        chargePointRepository.findByChargeBoxId(chargeBoxId).ifPresent(cb -> {
            cb.setLastHeartbeat(Instant.now());
            chargePointRepository.save(cb);
        });
    }

    // ------------------------------------------------
    // Dashboard / UI
    // ------------------------------------------------
    public List<ChargePoint> getAll() {
        return chargePointRepository.findAll();
    }
}
