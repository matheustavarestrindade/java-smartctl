package com.matheustt.smartctl;

import java.util.HashMap;

import lombok.Data;

@Data
public class SMARTInformation {

    private final HashMap<SmartAttribute, SmartData> smartData;

    // Other data
    private final Integer currentTemperature;
    private final Integer powerOnMinutes;
    private final Integer powerCycleCount;

    public boolean hasSmartAttribute(SmartAttribute attribute) {
        return !smartData.isEmpty() && smartData.containsKey(attribute);
    }

}
