package com.matheustt.smartctl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SmartCTL {

    private static final String SMART_CMD = "smartctl";
    private static final String JSON_FLAG = "--json";
    private static final String SCAN_FLAG = "--scan";
    private static final String INFORMATION_FLAG = "--info";
    private static final String SMART_FLAG = "--xall";

    public static List<StorageDevice> getStorageDevices(boolean loadManufacturer, boolean loadSmartData) {
        List<StorageDevice> storageDevices = new ArrayList<StorageDevice>();
        DeviceOutput output = getOutput(buildJsonFlag(SCAN_FLAG));

        if (output == null) {
            System.err.println("[JavaSmartCTL] Error: Could not get output from smartctl");
            return storageDevices;
        }

        if (output.getErrorLog().size() > 0) {
            System.err.println("[JavaSmartCTL] Error: Could not get output from smartctl");
            output.getErrorLog().stream().forEach(System.out::println);
            return storageDevices;
        }

        String jsonLines = String.join("", output.getOutputLog());
        JsonObject json = JsonParser.parseString(jsonLines).getAsJsonObject();

        if (!json.has("devices")) return storageDevices;

        JsonArray devices = json.get("devices").getAsJsonArray();
        for (int index = 0; index < devices.size(); index++) {
            JsonObject device = devices.get(index).getAsJsonObject();
            // @formatter:off
            /*
                {
                "name": "/dev/sda",
                "info_name": "/dev/sda",
                "type": "ata",
                "protocol": "ATA"
                },
             */

            String deviceName = device.has("name") ? device.get("name").getAsString() : "Unknown";
            String deviceInfoName = device.has("info_name") ? device.get("info_name").getAsString() : "Unknown";
            String deviceType = device.has("type") ? device.get("type").getAsString() : "Unknown";
            String deviceProtocol = device.has("protocol") ? device.get("protocol").getAsString() : "Unknown";
            // @formatter:on
            StorageDevice storageDevice = new StorageDevice(deviceName, deviceInfoName, deviceType, deviceProtocol);

            if (loadManufacturer) storageDevice.setManufacturer(getManufacturer(storageDevice));
            if (loadSmartData) storageDevice.setSmart(getSmartInformation(storageDevice));

            storageDevices.add(storageDevice);
        }

        return storageDevices;
    }

    private static SMARTInformation getSmartInformation(StorageDevice device) {
        DeviceOutput output = getOutput(buildJsonFlag(SMART_FLAG + " " + device.getInfoName()));
        if (output == null) {
            System.err.println("[JavaSmartCTL] Error: Could not get output for SmartInformation with device id: " + device.getInfoName() + " from smartctl");
            return null;
        }
        if (output.getErrorLog().size() > 0) {
            System.err.println("[JavaSmartCTL] Error: Could not get output for SmartInformation with device id: " + device.getInfoName() + " from smartctl");
            output.getErrorLog().stream().forEach(System.out::println);
            return null;
        }

        String jsonLines = String.join("", output.getOutputLog());
        JsonObject json = JsonParser.parseString(jsonLines).getAsJsonObject();
        if (!json.has("ata_smart_attributes") || !json.get("ata_smart_attributes").getAsJsonObject().has("table")) {
            return null;
        }

        HashMap<SmartAttribute, SmartData> smartData = new HashMap<SmartAttribute, SmartData>();
        JsonArray smartTable = json.get("ata_smart_attributes").getAsJsonObject().get("table").getAsJsonArray();

        for (int index = 0; index < smartTable.size(); index++) {

            JsonObject smartValue = smartTable.get(index).getAsJsonObject();
            int smartId = smartValue.get("id").getAsInt();
            SmartAttribute attribute = SmartAttribute.getById(smartId);
            if (attribute == null) continue;
            String value = smartValue.get("value").getAsString();
            String worst = smartValue.get("worst").getAsString();
            String raw = smartValue.has("raw") && smartValue.get("raw").getAsJsonObject().has("string") ? smartValue.get("raw").getAsJsonObject().get("string").getAsString() : "";

            SmartData data = new SmartData(value, worst, raw);
            smartData.put(attribute, data);
        }

        int currentTemperature = json.has("temperature") && json.get("temperature").getAsJsonObject().has("current") ? json.get("temperature").getAsJsonObject().get("current").getAsInt() : -1;
        int powerCycleCount = json.has("power_cycle_count") ? json.get("power_cycle_count").getAsInt() : -1;
        JsonObject powerOnTime = json.has("power_on_time") ? json.get("power_on_time").getAsJsonObject() : null;

        int powerOnHours = powerOnTime != null && powerOnTime.has("hours") ? powerOnTime.get("hours").getAsInt() : -1;
        int powerOnMinutes = powerOnTime != null && powerOnTime.has("minutes") ? powerOnTime.get("minutes").getAsInt() : -1;
        powerOnMinutes += powerOnHours * 60;

        SMARTInformation smart = new SMARTInformation(smartData, currentTemperature, powerOnMinutes, powerCycleCount);

        return smart;
    }

    private static DeviceManufacturer getManufacturer(StorageDevice device) {
        DeviceOutput output = getOutput(buildJsonFlag(INFORMATION_FLAG + " " + device.getInfoName()));
        if (output == null) {
            System.err.println("[JavaSmartCTL] Error: Could not get output for DeviceManufacturer with device id: " + device.getInfoName() + " from smartctl");
            return null;
        }
        if (output.getErrorLog().size() > 0) {
            System.err.println("[JavaSmartCTL] Error: Could not get output for DeviceManufacturer with device id: " + device.getInfoName() + " from smartctl");
            output.getErrorLog().stream().forEach(System.out::println);
            return null;
        }

        String jsonLines = String.join("", output.getOutputLog());
        JsonObject json = JsonParser.parseString(jsonLines).getAsJsonObject();

        String modelFamily = json.has("model_family") ? json.get("model_family").getAsString() : "Unknown";
        String modelName = json.has("model_name") ? json.get("model_name").getAsString() : "Unknown";
        String serialNumber = json.has("serial_number") ? json.get("serial_number").getAsString() : "Unknown";
        String firmwareVersion = json.has("firmware_version") ? json.get("firmware_version").getAsString() : "Unknown";
        Boolean supportTrim = json.has("trim") && json.getAsJsonObject("trim").has("supported") ? json.getAsJsonObject("trim").get("supported").getAsBoolean() : null;
        Boolean inSmartctlDatabase = json.has("in_smartctl_database") ? json.get("in_smartctl_database").getAsBoolean() : null;
        JsonObject smartSupport = json.has("smart_support") ? json.get("smart_support").getAsJsonObject() : null;

        Boolean hasSmartSupport = smartSupport != null && smartSupport.has("available") ? smartSupport.get("available").getAsBoolean() : null;
        Boolean hasSmartEnabled = smartSupport != null && smartSupport.has("enabled") ? smartSupport.get("enabled").getAsBoolean() : null;

        DeviceManufacturer manufacturer = new DeviceManufacturer(modelFamily, modelName, serialNumber, supportTrim, inSmartctlDatabase, hasSmartSupport, hasSmartEnabled, firmwareVersion);

        return manufacturer;

    }

    private static String buildJsonFlag(String flagCommand) {
        return SMART_CMD + " " + flagCommand + " " + JSON_FLAG;
    }

    private static DeviceOutput getOutput(String CMD) {
        DeviceOutput output = new DeviceOutput();
        try {
            // Run "netsh" Windows command
            Process process = Runtime.getRuntime().exec(CMD);

            // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output
            String s;
            while ((s = stdInput.readLine()) != null) {
                output.getOutputLog().add(s);
            }

            // Read command errors
            while ((s = stdError.readLine()) != null) {
                output.getErrorLog().add(s);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
        return output;
    }

}
