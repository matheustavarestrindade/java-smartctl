package com.matheustt.smartctl;

import lombok.Data;

@Data
public class StorageDevice {

    private final String name;
    private final String infoName;
    private final String type;
    private final String protocol;

    private SMARTInformation smart;
    private DeviceManufacturer manufacturer;

    public boolean hasManufacturar() {
        return manufacturer != null;
    }

    public boolean hasSmartData() {
        return smart != null;
    }

}
