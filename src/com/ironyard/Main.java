package com.ironyard;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        Scanner scanner = new Scanner(System.in);
        VehicleInfo myCar = new VehicleInfo();

        System.out.println("Enter VIN: ");
        Integer vin = scanner.nextInt();
        myCar.setVIN(vin);

        System.out.println("Odometer reading: ");
        Double odom = scanner.nextDouble();
        myCar.setOdometer(odom);

        System.out.println("Gallons of fuel consumed: ");
        Double gas = scanner.nextDouble();
        myCar.setConsumption(gas);

        System.out.println("Miles since last oil change: ");
        Double oil = scanner.nextDouble();
        myCar.setMilesSinceOil(oil);

        System.out.println("Engine size (liters): ");
        Double liters = scanner.nextDouble();
        myCar.setEngineSize(liters);

        TelematicsService.report(myCar);
    }
}
