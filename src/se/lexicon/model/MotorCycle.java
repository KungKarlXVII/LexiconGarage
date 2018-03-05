package se.lexicon.model;

public class MotorCycle extends Vehicle {

    private Integer topSpeed;

    public MotorCycle(String brand, String model, String regNo, Integer topSpeed) {
        super(brand, model);
        super.setNumberOfRegistration(regNo);
        super.setNumberOfWheels(2);
        this.topSpeed = topSpeed;
    }

    public MotorCycle() {
        this("", "", "", 0);
    }

    public Integer getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(Integer topSpeed) {
        this.topSpeed = topSpeed;
    }

    @Override
    public void getInformation() {
        System.out.println("Printing information from MotorCycle class...");
        System.out.println(getBrand() + " " + getModel() + ", reg-no: " + getNumberOfRegistration());
        System.out.println(getNumberOfWheels() + " wheels, " + topSpeed + " total top speed.");
    }
}
