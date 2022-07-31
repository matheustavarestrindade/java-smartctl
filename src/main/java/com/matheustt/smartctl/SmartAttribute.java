package com.matheustt.smartctl;

import lombok.Getter;

public enum SmartAttribute {
    READ_ERROR_RATE(1, "Read Error Rate"), THROUGHPUT_PERFORMANCE(2, "Throughput Performance"),
    SPINUP_TIME(3, "Spin-Up Time"), START_STOP_COUNT(4, "Start/Stop Count"),
    REALLOCATED_SECTORS_COUNT(5, "Reallocated Sectors Count"), READ_CHANNEL_MARGIN(6, "Read Channel Margin"),
    SEEK_ERROR_RATE(7, "Seek Error Rate"), SEEK_TIME_PERFORMANCE(8, "Seek Time Performance"),
    POWERON_HOURS(9, "Power-On Hours"), SPIN_RETRY_COUNT(10, "Spin Retry Count"),
    RECALIBRATION_RETRIES(11, "Recalibration Retries"), POWER_CYCLE_COUNT(12, "Power Cycle Count"),
    SOFT_READ_ERROR_RATE_13(13, "Soft Read Error Rate"), CURRENT_HELIUM_LEVEL(22, "Current Helium Level"),
    AVAILABLE_RESERVED_SPACE(170, "Available Reserved Space"), SSD_PROGRAM_FAIL_COUNT(171, "SSD Program Fail Count"),
    SSD_ERASE_FAIL_COUNT(172, "SSD Erase Fail Count"), SSD_WEAR_LEVELING_COUNT(173, "SSD Wear Leveling Count"),
    UNEXPECTED_POWER_LOSS_COUNT(174, "Unexpected Power Loss Count"),
    POWER_LOSS_PROTECTION_FAILURE(175, "Power Loss Protection Failure"), ERASE_FAIL_COUNT_176(176, "Erase Fail Count"),
    WEAR_RANGE_DELTA(177, "Wear Range Delta"), USED_RESERVED_BLOCK_COUNT(178, "Used Reserved Block Count"),
    USED_RESERVED_BLOCK_COUNT_TOTAL(179, "Used Reserved Block Count Total"),
    UNUSED_RESERVED_BLOCK_COUNT_TOTAL(180, "Unused Reserved Block Count Total"),
    PROGRAM_FAIL_COUNT_TOTAL(181, "Program Fail Count Total"), ERASE_FAIL_COUNT_182(182, "Erase Fail Count"),
    SATA_DOWNSHIFT_ERROR_COUNT(183, "SATA Downshift Error Count"),
    ENDTOEND_ERROR_IOEDC(184, "End-to-End error / IOEDC"), HEAD_STABILITY(185, "Head Stability"),
    INDUCED_OPVIBRATION_DETECTION(186, "Induced Op-Vibration Detection"),
    REPORTED_UNCORRECTABLE_ERRORS(187, "Reported Uncorrectable Errors"), COMMAND_TIMEOUT(188, "Command Timeout"),
    HIGH_FLY_WRITES(189, "High Fly Writes"), TEMPERATURE_DIFFERENCE(190, "Temperature Difference"),
    GSENSE_ERROR_RATE(191, "G-sense Error Rate"), POWEROFF_RETRACT_COUNT(192, "Power-off Retract Count"),
    LOAD_CYCLE_COUNT(193, "Load Cycle Count"), TEMPERATURE(194, "Temperature"),
    HARDWARE_ECC_RECOVERED(195, "Hardware ECC Recovered"), REALLOCATION_EVENT_COUNT(196, "Reallocation Event Count"),
    CURRENT_PENDING_SECTOR_COUNT(197, "Current Pending Sector Count"),
    OFFLINE_UNCORRECTABLE_SECTOR_COUNT(198, "(Offline) Uncorrectable Sector Count"),
    ULTRADMA_CRC_ERROR_COUNT(199, "UltraDMA CRC Error Count"), MULTIZONE_ERROR_RATE(200, "Multi-Zone Error Rate"),
    WRITE_ERROR_RATE(200, "Write Error Rate"), SOFT_READ_ERROR_RATE_201(201, "Soft Read Error Rate"),
    DATA_ADDRESS_MARK_ERRORS(202, "Data Address Mark errors"), RUN_OUT_CANCEL(203, "Run Out Cancel"),
    SOFT_ECC_CORRECTION(204, "Soft ECC Correction"), THERMAL_ASPERITY_RATE(205, "Thermal Asperity Rate"),
    FLYING_HEIGHT(206, "Flying Height"), SPIN_HIGH_CURRENT(207, "Spin High Current"), SPIN_BUZZ(208, "Spin Buzz"),
    OFFLINE_SEEK_PERFORMANCE(209, "Offline Seek Performance"),
    VIBRATION_DURING_WRITE_210(210, "Vibration During Write"),
    VIBRATION_DURING_WRITE_211(211, "Vibration During Write"), SHOCK_DURING_WRITE(212, "Shock During Write"),
    DISK_SHIFT(220, "Disk Shift"), G_SENSE_ERROR_RATE(221, "G-Sense Error Rate"), LOADED_HOURS(222, "Loaded Hours"),
    LOAD_UNLOAD_RETRY_COUNT(223, "Load/Unload Retry Count"), LOAD_FRICTION(224, "Load Friction"),
    LOAD_UNLOAD_CYCLE_COUNT(225, "Load/Unload Cycle Count"), LOAD_INTIME(226, "Load 'In'-time"),
    TORQUE_AMPLIFICATION_COUNT(227, "Torque Amplification Count"),
    POWEROFF_RETRACT_CYCLE(228, "Power-Off Retract Cycle"), GMR_HEAD_AMPLITUDE(230, "GMR Head Amplitude"),
    LIFE_LEFT(231, "Life Left"), ENDURANCE_REMAINING(232, "Endurance Remaining"),
    MEDIA_WEAROUT_INDICATOR(233, "Media Wearout Indicator"),
    AVERAGE_ERASE_COUNT_AND__MAXIMUM_ERASE_COUNT(234, "Average erase count AND  Maximum Erase Count"),
    GOOD_BLOCK_COUNT_AND_SYSTEM_FREE__BLOCK_COUNT(235, "Good Block Count AND System(Free) Block Count"),
    HEAD_FLYING_HOURS(240, "Head Flying Hours"), TOTAL_LBAS_WRITTEN(241, "Total LBAs Written"),
    TOTAL_LBAS_READ(242, "Total LBAs Read"), TOTAL_LBAS_WRITTEN_EXPANDED(243, "Total LBAs Written Expanded"),
    TOTAL_LBAS_READ_EXPANDED(244, "Total LBAs Read Expanded"), NAND_WRITES_1_GIB(249, "NAND Writes (1GiB)"),
    READ_ERROR_RETRY_RATE(250, "Read Error Retry Rate"), MINIMUM_SPARES_REMAINING(251, "Minimum Spares Remaining"),
    NEWLY_ADDED_BAD_FLASH_BLOCK(252, "Newly Added Bad Flash Block"), FREE_FALL_PROTECTION(254, "Free Fall Protection");

    @Getter
    private int id;
    @Getter
    private String name;

    SmartAttribute(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public static SmartAttribute getById(int id) {
        for (SmartAttribute attribute : values()) {
            if (attribute.getId() == id) {
                return attribute;
            }
        }
        return null;
    }

}
