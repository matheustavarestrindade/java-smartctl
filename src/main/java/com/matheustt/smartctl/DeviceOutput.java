package com.matheustt.smartctl;

import java.util.ArrayList;

import lombok.Data;

@Data
public class DeviceOutput {

    private final ArrayList<String> errorLog = new ArrayList<>();
    private final ArrayList<String> outputLog = new ArrayList<>();

}
