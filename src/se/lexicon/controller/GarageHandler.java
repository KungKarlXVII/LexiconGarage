package se.lexicon.controller;

import se.lexicon.model.*;
import se.lexicon.utilities.Utilities;

import java.util.*;

public class GarageHandler {

    private List<Garage> availableGarages;
    private Garage activeGarage;

    private List<Vehicle> knownVehicles;

    public GarageHandler() {
        this.knownVehicles = new ArrayList<>();
        this.availableGarages = new ArrayList<>();
        setUpExampleStartingGarage();
        availableGarages.add(activeGarage);
    }

    private void setUpExampleStartingGarage() {
        if(availableGarages.size() == 0) {
            Garage newGarage = new Garage();
            newGarage.setGarageLimit(5);
            newGarage.setLocation("Globen");
            this.activeGarage = newGarage;
        }
    }

    public void showGarageInventory() {
        System.out.println("Printing " + activeGarage.getLocation() + "'s current inventory...");
        System.out.println(activeGarage.getParkedVehicles().size() + "/" + activeGarage.getGarageLimit() + " spots used");
        System.out.println("------------");
        for(Vehicle vehicle : activeGarage.getParkedVehicles()) {
            vehicle.getInformation();
            System.out.println("------------");
        }
    }


    /**
     * Add vehicle to knownVehicles for future referencing.
     * @param vehicle
     * @throws Exception
     */
    private void addToKnownVehicles(Vehicle vehicle) throws Exception {
        if(knownVehicles.contains(vehicle)) throw new Exception("Already known...");
        knownVehicles.add(vehicle);
    }


    /**
     * Returns the vehicle with corresponding regNo if any.
     * Otherwise returns Optional.empty()
     * @param regNo
     * @return
     */
    private Optional<Vehicle> getVehicleFromKnownList(String regNo) {

        for(Vehicle vehicle : knownVehicles) {
            if(vehicle.getNumberOfRegistration().equalsIgnoreCase(regNo)) {
                return Optional.of(vehicle);
            }
        }
        return Optional.empty();
    }


    /**
     * Set up a new vehicle with corresponding information.
     * If registration number is already in knownVehicles the method will return with that object.
     *
     * @param scan
     * @return Returns vehicle either from knownVehicles by regNo or creates a new vehicle with scanner information.
     * @throws Exception
     */
    private Vehicle setupNewVehicle(Scanner scan) throws Exception {

        Vehicle newVehicle = null;

        // Begin with entering regNo. If regNo present in knownVehicles no setup is needed.
        System.out.print("Enter reg-number: ");
        String regNo = scan.next();

        // Optional return from method, if present use that vehicle and return.
        if(getVehicleFromKnownList(regNo).isPresent()) {
            Vehicle existingVehicle = getVehicleFromKnownList(regNo).get();
            System.out.println(existingVehicle);
            return existingVehicle;
        }

        // Create a vehicle by type of Enum
        VehicleType createType = VehicleType.UNKNOWN;
        System.out.print("Enter vehicle type: ");
        String type = scan.next();

        switch(type.toLowerCase()) {
            case "car":
                createType = VehicleType.CAR;
                newVehicle = new Car();
                break;
            case "bus":
                createType = VehicleType.BUS;
                newVehicle = new Bus();
                break;
            case "boat":
                createType = VehicleType.BOAT;
                newVehicle = new Boat();
                break;
            case "airplane":
                createType = VehicleType.AIRPLANE;
                newVehicle = new Airplane();
                break;
            case "motorcycle":
                createType = VehicleType.MOTORCYCLE;
                newVehicle = new MotorCycle();
                break;
            default:
                System.out.println("Unknown vehicle type...");
                throw new Exception("Vehicle type not set up properly...");
        }

        // Setup type for created object.
        newVehicle.setType(createType);
        newVehicle.setNumberOfRegistration(regNo);

        // If type is set up, we can be specific here
        // Ask for brand of vehicle for later.
        System.out.print("Vehicle is of brand: ");
        String brand = scan.next();
        newVehicle.setBrand(brand);

        // Ask for model of vehicle for later.
        System.out.print("Vehicle is of model: ");
        String model = scan.next();
        newVehicle.setModel(model);

        // Ask for specific information according to vehicle type.
        try {

            switch(newVehicle.getType()) {
                case CAR:
                    System.out.print("Fill in number of doors on your car: ");
                    Car car = (Car) newVehicle;
                    car.setNumberOfDoors(Integer.parseInt(scan.next()));
                    break;

                case BUS:
                    System.out.print("Fill in total passenger capacity: ");
                    Bus bus = (Bus) newVehicle;
                    bus.setNumberOfPassengerSeats(Integer.parseInt(scan.next()));
                    break;

                case BOAT:
                    System.out.print("Fill in total motor effect (HP): ");
                    Boat boat = (Boat) newVehicle;
                    boat.setHorsePower(Integer.parseInt(scan.next()));
                    break;

                case AIRPLANE:
                    System.out.print("Fill in number of engines: ");
                    Airplane airplane = (Airplane) newVehicle;
                    airplane.setNumberOfEngines(Integer.parseInt(scan.next()));
                    break;

                case MOTORCYCLE:
                    System.out.print("Fill in top speed: ");
                    MotorCycle mc = (MotorCycle) newVehicle;
                    mc.setTopSpeed(Integer.parseInt(scan.next()));
                    break;

                case UNKNOWN:
                    break;
            }

        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        }

        addToKnownVehicles(newVehicle);
        return newVehicle;

    }

    /**
     * Park vehicle in active garage.
     * Enter registration number and get vehicle from already knownVehicles
     * or create a new vehicle and park it.
     *
     * Vehicle Types: CAR, BUS, BOAT, AIRPLANE, MOTORCYCLE
     *
     * @param scan
     */
    public void parkVehicle(Scanner scan) {
        if(activeGarage.getParkedVehicles().size() >= activeGarage.getGarageLimit()) {
            System.out.println("Garage is currently full...");
            return;
        }

        Vehicle parkingVehicle = null;

        try {
            parkingVehicle = setupNewVehicle(scan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(parkingVehicle == null || parkingVehicle.isParked()) {
            System.out.println("Vehicle invalid or already parked");
            return;
        }

        activeGarage.parkVehicle(parkingVehicle);
        System.out.println(parkingVehicle + " have been parked to the garage");
    }

    public void unparkVehicle(Scanner scan) {

        if(activeGarage.getParkedVehicles().size() == 0) {
            System.out.println("Garage is empty...");
            return;
        }

        int index = 0;
        for(Vehicle vehicle : activeGarage.getParkedVehicles()) {
            System.out.println("[" + index++ + "] " + vehicle);
        }

        System.out.print("What vehicle do you want to unpark? ");
        Vehicle whatVehicle = activeGarage.getParkedVehicles().get(Integer.parseInt(scan.next()));
        activeGarage.unParkVehicle(whatVehicle);

        System.out.println(whatVehicle + " have been unparked from " + activeGarage.getLocation());
    }



    public void listAllKnownVehicles() {
        System.out.println("----------------");
        for(Vehicle vehicle : knownVehicles) {
            System.out.println(vehicle);
            System.out.println("----------------");
        }
    }

    /**
     * Selects an active garage from index in availableGarages.
     * Uses scanner to select the index after iterating over all locations.
     * @param scan
     */
    public void selectActiveGarage(Scanner scan) {
        int index = 0;
        for(Garage garage : availableGarages) {
            System.out.println("[" + index++ + "] " + garage.getLocation());
        }
        System.out.print("Please select a garage to manage: ");
        try {
            Integer keyboard = Integer.parseInt(scan.next());
            activeGarage = availableGarages.get(keyboard);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("You have chosen to manage " + activeGarage.getLocation());
    }


    /**
     * Creates a new Garage and adds it to availableGarages.
     * Makes the newly created garage the active one at method end.
     * Fill in location name and total available parking spots.
     *
     * @param scan
     */
    public void openNewGarage(Scanner scan) {

        Garage newGarage = new Garage();

        System.out.print("Where is your new garage located? ");
        String garageLocation = scan.next();
        newGarage.setLocation(garageLocation);

        System.out.print("How many parking spots are there? ");
        boolean isSuccess = false;
        while(!isSuccess) {

            try {
                Integer garageLimit = Integer.parseInt(scan.next());
                newGarage.setGarageLimit(garageLimit);
                isSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        availableGarages.add(newGarage);
        activeGarage = newGarage;
        System.out.println("A new garage have been opened and selected as active.");
        System.out.println("Active garage is now : " + activeGarage.getLocation());
    }

    public void saveInventory() {
        Utilities.saveInventory(availableGarages);
        Utilities.saveKnownVehicles(knownVehicles);
    }

    public void loadInventory() {
        this.availableGarages = Utilities.loadInventory();
        this.knownVehicles = Utilities.loadKnownVehicles();
        this.activeGarage = availableGarages.get(0);
    }

}
