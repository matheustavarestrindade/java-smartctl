package com.matheustt.smartctl;

import lombok.Data;

@Data
public class DeviceManufacturer {

    private final String modelFamily;
    private final String modelName;
    private final String serialNumber;
    private final Boolean supportTrim;
    private final Boolean inSmartctlDatabase;
    private final Boolean hasSmartSupport;
    private final Boolean hasSmartEnabled;
    private final String firmwareVersion;

}
