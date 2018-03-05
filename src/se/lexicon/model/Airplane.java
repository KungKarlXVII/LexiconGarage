package se.lexicon.model;

public class Airplane extends Vehicle {

    private Integer numberOfEngines;

    public Airplane(String brand, String model, String regNo, Integer noOfEngines) {
        super(brand, model);
        super.setNumberOfRegistration(regNo);
        super.setNumberOfWheels(3);
        this.numberOfEngines = noOfEngines;
    }

    public Airplane() {
        this("", "", "", 0);
    }

    public Integer getNumberOfEngines() {
        return numberOfEngines;
    }

    public void setNumberOfEngines(Integer numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
    }

    @Override
    public void getInformation() {
        System.out.println("Printing information from Airplane class...");
        System.out.println(getBrand() + " " + getModel() + ", reg-no: " + getNumberOfRegistration());
        System.out.println(getNumberOfWheels() + " wheels, " + numberOfEngines + " number of engines.");
    }
}
