package se.lexicon.model;

public class Car extends Vehicle {

    private Integer numberOfDoors;

    public Car(String brand, String model, String regNo) {
        super(brand, model);
        super.setNumberOfRegistration(regNo);
        super.setNumberOfWheels(4);
        super.setType(VehicleType.CAR);
        this.numberOfDoors = 5;
    }

    public Car() {
        this("","","");
    }

    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Integer numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public void getInformation() {
        System.out.println("Printing information from Car class...");
        System.out.println(getBrand() + " " + getModel() + ", reg-no: " + getNumberOfRegistration());
        System.out.println(getNumberOfWheels() + " wheels, " + numberOfDoors + " doors.");
    }
}
