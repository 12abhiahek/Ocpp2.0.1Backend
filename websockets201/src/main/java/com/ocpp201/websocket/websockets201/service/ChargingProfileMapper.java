package com.ocpp201.websocket.websockets201.service;

import com.ocpp201.websocket.websockets201.dto.OcppChargingProfile;

import com.ocpp201.websocket.websockets201.dto.OcppChargingSchedulePeriod;
import com.ocpp201.websocket.websockets201.model.ChargingProfile;
import com.ocpp201.websocket.websockets201.model.ChargingSchedulePeriod;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChargingProfileMapper {
//
//    public OcppChargingProfile toOcpp(
//            com.ocpp201.websocket.websockets201.model.ChargingProfile dbProfile
//    ) {
//
//        ChargingSchedule sch = dbProfile.getSchedules().get(0);
//        ChargingSchedulePeriod p = sch.getPeriods().get(0);
//
//        OcppChargingSchedulePeriod ocppPeriod =
//                new OcppChargingSchedulePeriod();
//        ocppPeriod.setStartPeriod(p.getStartPeriod());
//        ocppPeriod.setLimit(p.getLimit().doubleValue());
//
//        OcppChargingSchedule ocppSchedule =
//                new OcppChargingSchedule();
//        ocppSchedule.setChargingRateUnit(sch.getChargingRateUnit());
//        ocppSchedule.setChargingSchedulePeriod(
//                List.of(ocppPeriod)
//        );
//
//        OcppChargingProfile ocppProfile =
//                new OcppChargingProfile();
//        ocppProfile.setId(dbProfile.getProfileId());
//        ocppProfile.setStackLevel(dbProfile.getStackLevel());
//        ocppProfile.setChargingProfilePurpose(
//                dbProfile.getChargingProfilePurpose()
//        );
//        ocppProfile.setChargingProfileKind(
//                dbProfile.getChargingProfileKind()
//        );
//        ocppProfile.setChargingSchedule(ocppSchedule);
//
//        return ocppProfile;
//    }



    public OcppChargingProfile toOcpp(ChargingProfile dbProfile) {

        // ================= MAP PERIODS =================
        List<OcppChargingSchedulePeriod> ocppPeriods =
                dbProfile.getSchedulePeriods()
                        .stream()
                        .map(p -> {
                            OcppChargingSchedulePeriod dto =
                                    new OcppChargingSchedulePeriod();
                            dto.setStartPeriod(p.getStartPeriod());
                            dto.setLimit(p.getPowerLimit());
                            dto.setNumberPhases(p.getNumberPhases());
                            return dto;
                        })
                        .toList();

        // ================= MAP PROFILE =================
        OcppChargingProfile ocppProfile =
                new OcppChargingProfile();

        ocppProfile.setChargingProfileId(dbProfile.getProfileId());
        ocppProfile.setStackLevel(dbProfile.getStackLevel());
        ocppProfile.setChargingProfilePurpose(
                dbProfile.getChargingProfilePurpose()
        );
        ocppProfile.setChargingProfileKind(
                dbProfile.getChargingProfileKind()
        );
        ocppProfile.setChargingRateUnit(
                dbProfile.getChargingRateUnit()
        );
        ocppProfile.setSchedulePeriods(ocppPeriods);

        return ocppProfile;
    }
}
