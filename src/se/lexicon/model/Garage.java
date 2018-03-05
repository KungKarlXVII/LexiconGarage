package se.lexicon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Garage implements Serializable {

    private Integer garageLimit;
    private String location;
    private List<Vehicle> parkedVehicles;

    public Garage() {
        this.garageLimit = 5;
        this.location = "Default";
        this.parkedVehicles = new ArrayList<>(garageLimit);
    }

    public Integer getGarageLimit() {
        return garageLimit;
    }

    public void setGarageLimit(Integer garageLimit) {
        this.garageLimit = garageLimit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if(location != null && location != "") this.location = location;
        else System.out.println("Invalid location name provided");
    }

    public List<Vehicle> getParkedVehicles() {
        return Collections.unmodifiableList(parkedVehicles);
    }

    public void parkVehicle(Vehicle vehicle) {
        if(vehicle == null) {
            System.out.println("No vehicle sent for parking...");
            return;
        }
        if(parkedVehicles.size() >= garageLimit) {
            System.out.println("Garage is currently full...");
            return;
        }
        if(vehicle.isParked() || parkedVehicles.contains(vehicle)) {
            System.out.println("Vehicle is already parked...");
            return;
        }

        vehicle.park();
        this.parkedVehicles.add(vehicle);
    }

    public void unParkVehicle(Vehicle vehicle) {
        if(vehicle == null || !parkedVehicles.contains(vehicle)) {
            System.out.println("The vehicle is not in the garage...");
            return;
        }
        if(!vehicle.isParked()) {
            System.out.println("The vehicle is not parked...");
            return;
        }
        vehicle.unpark();
        this.parkedVehicles.remove(vehicle);
    }

}
