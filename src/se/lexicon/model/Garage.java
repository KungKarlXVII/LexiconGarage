package se.lexicon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Garage implements Serializable {

    private static final long serialVersionUID = 5661669814670995975L;

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

    public void setGarageLimit(Integer newGarageLimit) {
        if(newGarageLimit <= 0) {
            System.out.println("Error: I'm sorry that is not possible.");
            return;
        }
        if(this.garageLimit == newGarageLimit) {
            System.out.println("Error: Garage already have " + newGarageLimit + "parking spots.");
            return;
        }
        if(newGarageLimit < this.parkedVehicles.size()) {
            System.out.println("Error: I cant let you destroy the vehicles parked inside.");
            return;
        }
        this.garageLimit = newGarageLimit;
        System.out.println(location + " now have " + newGarageLimit + " parking spots.");
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Garage : ");
        sb.append(location + " [ " + parkedVehicles.size() + "/" + garageLimit + " ]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Garage garage = (Garage) o;
        return Objects.equals(garageLimit, garage.garageLimit) &&
                Objects.equals(location, garage.location) &&
                Objects.equals(parkedVehicles, garage.parkedVehicles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(garageLimit, location, parkedVehicles);
    }
}
