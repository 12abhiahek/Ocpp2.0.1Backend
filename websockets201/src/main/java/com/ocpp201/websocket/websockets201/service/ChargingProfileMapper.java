package com.ocpp201.websocket.websockets201.service;

import com.ocpp201.websocket.websockets201.dto.OcppChargingProfile;

import com.ocpp201.websocket.websockets201.dto.OcppChargingSchedulePeriod;
import com.ocpp201.websocket.websockets201.model.ChargingProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class ChargingProfileMapper {


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

        ocppProfile.setValidFrom(String.valueOf(dbProfile.getValidFrom()));
        ocppProfile.setValidTo(String.valueOf(dbProfile.getValidTo()));
        ocppProfile.setRecurrenceKind(Collections.singletonList(dbProfile.getRecurrenceKind()));

        ocppProfile.setSchedulePeriods(ocppPeriods);

        log.info(
                "[MAPPER] Built OCPP profile profileId={} stack={} limit={}{}",
                ocppProfile.getChargingProfileId(),
                ocppProfile.getStackLevel(),
                ocppPeriods.get(0).getLimit(),
                ocppProfile.getChargingRateUnit()
        );

        return ocppProfile;
    }
}
