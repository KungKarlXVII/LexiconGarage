package se.lexicon.model;

public class Bus extends Vehicle {

    private Integer numberOfPassengerSeats;

    public Bus(String brand, String model, String regNo, Integer numberOfPassengerSeats) {
        super(brand, model);
        super.setNumberOfRegistration(regNo);
        super.setNumberOfWheels(8);
        super.setType(VehicleType.BUS);
        this.numberOfPassengerSeats = numberOfPassengerSeats;
    }

    public Bus() {
        this("","", "", 0);
    }

    public Integer getNumberOfPassengerSeats() {
        return numberOfPassengerSeats;
    }

    public void setNumberOfPassengerSeats(Integer numberOfPassengerSeats) {
        this.numberOfPassengerSeats = numberOfPassengerSeats;
    }

    @Override
    public void getInformation() {
        System.out.println("Printing information from Bus class...");
        System.out.println(super.toString() + "\n" + getNumberOfWheels() + " wheels, " + numberOfPassengerSeats + " passenger seats.");
    }
}
