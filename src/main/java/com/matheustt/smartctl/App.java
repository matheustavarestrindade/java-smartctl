package com.matheustt.smartctl;

import java.util.List;
import java.util.Map.Entry;

public final class App {

    public static void main(String[] args) {

        List<StorageDevice> devices = SmartCTL.getStorageDevices(true, true);

        for (StorageDevice device : devices) {
            System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            System.out.println();
            System.out.println("Device: " + device.getName());
            System.out.println("Info Name: " + device.getInfoName());
            System.out.println("Type: " + device.getType());
            System.out.println("Protocol: " + device.getProtocol());
            System.out.println();
            System.out.println("==========================================================");
            System.out.println();
            System.out.println("Has Manufacturer: " + device.hasManufacturar());
            System.out.println("Model Family: " + device.getManufacturer().getModelFamily());
            System.out.println("Model Name: " + device.getManufacturer().getModelName());
            System.out.println("Serial Number: " + device.getManufacturer().getSerialNumber());
            System.out.println("Support Trim: " + device.getManufacturer().getSupportTrim());
            System.out.println("In SmartCTL Database: " + device.getManufacturer().getInSmartctlDatabase());
            System.out.println("Smart Support: " + device.getManufacturer().getHasSmartSupport());
            System.out.println("Smart Enabled: " + device.getManufacturer().getHasSmartEnabled());
            System.out.println();
            System.out.println("==========================================================");
            System.out.println();
            System.out.println("Has Smart Data: " + device.hasSmartData());
            if (device.hasSmartData()) {
                System.out.println("Current temp: " + device.getSmart().getCurrentTemperature());
                System.out.println("Power cicle count: " + device.getSmart().getPowerCycleCount());
                System.out.println("Power on Min: " + device.getSmart().getPowerOnMinutes());
                for (Entry<SmartAttribute, SmartData> et : device.getSmart().getSmartData().entrySet()) {
                    System.out.println("Attribute: " + et.getKey().getName());
                    System.out.println("\t value: " + et.getValue().getValue());
                    System.out.println("\t worst: " + et.getValue().getWorst());
                    System.out.println("\t raw: " + et.getValue().getRaw());
                }
            }
            System.out.println();
            System.out.println("==========================================================");
            System.out.println();
            System.out.println();
            System.out.println();
        }

    }
}
