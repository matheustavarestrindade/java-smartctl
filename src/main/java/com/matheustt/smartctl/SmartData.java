package com.matheustt.smartctl;

import lombok.Data;

@Data
public class SmartData {

    private final String value;
    private final String worst;
    private final String raw;

}
