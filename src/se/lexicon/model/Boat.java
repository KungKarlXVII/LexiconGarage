package se.lexicon.model;

public class Boat extends Vehicle {

    private Integer horsePower;

    public Boat(String brand, String model, String regNo, Integer horsePower) {
        super(brand, model);
        super.setNumberOfRegistration(regNo);
        this.horsePower = horsePower;
    }

    public Boat() {
        this("","","",0);
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public void getInformation() {
        System.out.println("Printing information from Boat class...");
        System.out.println(super.toString() + "\n" + getNumberOfWheels() + " wheels, " + horsePower + " horse power.");
    }
}
