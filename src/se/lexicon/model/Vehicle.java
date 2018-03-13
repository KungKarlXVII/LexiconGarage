package se.lexicon.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Vehicle implements Serializable {

    private String brand;
    private String model;
    private VehicleType type;
    private Integer numberOfWheels;
    private String numberOfRegistration;
    private boolean isParked;

    public Vehicle(String brand, String model) {
        this.brand = brand;
        this.model = model;
        this.numberOfWheels = 0;
        this.numberOfRegistration = "unavailable";
        this.type = null;
        isParked = false;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(Integer numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public String getNumberOfRegistration() {
        return numberOfRegistration;
    }

    public void setNumberOfRegistration(String numberOfRegistration) {
        this.numberOfRegistration = numberOfRegistration;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public boolean isParked() {
        return isParked;
    }

    public void park() {
        this.isParked = true;
    }

    public void unpark() {
        this.isParked = false;
    }

    public abstract void getInformation();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(numberOfRegistration, vehicle.numberOfRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfRegistration);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vehicle : " + type + " - ");
        sb.append(brand + " " + model);
        sb.append(", reg. no: " + numberOfRegistration);
        return sb.toString();
    }
}
