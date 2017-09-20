package com.ironyard;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class VehicleInfo {
    private int VIN;
    private double odometer;
    private double consumption;
    private double milesSinceOil;
    private double engineSize;

    DecimalFormat df = new DecimalFormat("#.0");

    public VehicleInfo() {

    }

    public int getVIN() {
        return VIN;
    }

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getMilesSinceOil() {
        return milesSinceOil;
    }

    public void setMilesSinceOil(double milesSinceOil) {
        this.milesSinceOil = milesSinceOil;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }

    public String milesPerGallon() {
        return (df.format(this.odometer / this.consumption));
    }
}
