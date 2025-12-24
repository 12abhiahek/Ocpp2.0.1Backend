package com.ocpp201.websocket.websockets201.dto;

import com.ocpp201.websocket.websockets201.model.ChargingProfile;
import lombok.Data;

@Data
public class SetChargingProfileRequest {

    private Integer evseId;
    private OcppChargingProfile chargingProfile; //dto
}
