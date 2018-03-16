package se.lexicon.controller;

import se.lexicon.exception.GarageException;
import se.lexicon.model.*;
import se.lexicon.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GarageHandler {

    // save all known vehicles for faster setup of vehicles at parking time
    private List<Vehicle> knownVehicles;

    // save all available garages so we can switch between them
    private List<Garage> availableGarages;

    // a reference to the acting garage
    private Garage activeGarage;

    public GarageHandler() {
        this.knownVehicles = new ArrayList<>();
        this.availableGarages = new ArrayList<>();
    }

    private void setUpExampleStartingGarage() {
        if (availableGarages.size() == 0) {
            Garage newGarage = new Garage();
            newGarage.setGarageLimit( 5 );
            newGarage.setLocation( "Globen" );
            this.activeGarage = newGarage;
            this.availableGarages.add( activeGarage );
        }
    }

    /**
     * Print information about the cars and available parking spots in the garage.
     */
    public void showGarageInventory() {
        System.out.println( "Printing " + activeGarage.getLocation() + "'s current inventory..." );
        System.out.println( activeGarage.getParkedVehicles().size() + "/" + activeGarage.getGarageLimit() + " spots used" );
        System.out.println( "------------" );
        for (Vehicle vehicle : activeGarage.getParkedVehicles()) {
            vehicle.getInformation();
            System.out.println( "------------" );
        }
    }

    /**
     * Add vehicle to knownVehicles for future reference.
     *
     * @param vehicle
     * @throws Exception
     */
    private void addToKnownVehicles(Vehicle vehicle) throws GarageException {
        if (knownVehicles.contains( vehicle )) throw new GarageException( "Already known..." );
        knownVehicles.add( vehicle );
    }


    /**
     * Returns the vehicle with corresponding regNo if any.
     * Otherwise returns Optional.empty()
     *
     * @param regNo search by registration number. Returns empty Optional if not found.
     * @return Optional Vehicle or empty
     */
    private Optional<Vehicle> getVehicleFromKnownList(String regNo) {

        for (Vehicle vehicle : knownVehicles) {
            if (vehicle.getNumberOfRegistration().equalsIgnoreCase( regNo )) {
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
    private Vehicle setupNewVehicle(Scanner scan) throws GarageException {

        // Set up reference to vehicle to build on.
        Vehicle newVehicle = null;

        // Begin with entering regNo. If regNo present in knownVehicles no setup is needed.
        System.out.print( "Enter reg-number: " );
        String regNo = scan.next();

        // Optional return from method, if present use that vehicle and return.
        if (getVehicleFromKnownList( regNo ).isPresent()) {
            Vehicle existingVehicle = getVehicleFromKnownList( regNo ).get();
            System.out.println( "Found vehicle in database... Using " + existingVehicle );
            return existingVehicle;
        }


        // Create a vehicle by type of Enum
        int index = 0;
        System.out.print( "Enter vehicle type: " );

        // Iterate all the available types
        for (VehicleType type : VehicleType.values()) {
            System.out.print( "[" + index++ + "] " + type + " " );
        }

        String option = scan.next();
        VehicleType createType = null;

        try {

            switch (option) {

                case "0":

                    // Create a new car object with default properties.
                    createType = VehicleType.CAR;
                    newVehicle = new Car();

                    System.out.print( "Fill in number of doors on your car: " );

                        /* Up-cast to set specific properties on objects */
                    Car car = (Car) newVehicle;
                    car.setNumberOfDoors( Integer.parseInt( scan.next() ) );

                    break;

                case "1":

                    createType = VehicleType.BUS;
                    newVehicle = new Bus();

                    System.out.print( "Fill in total passenger capacity: " );

                    Bus bus = (Bus) newVehicle;
                    bus.setNumberOfPassengerSeats( Integer.parseInt( scan.next() ) );

                    break;

                case "2":

                    createType = VehicleType.BOAT;
                    newVehicle = new Boat();

                    System.out.print( "Fill in total motor effect (HP): " );

                    Boat boat = (Boat) newVehicle;
                    boat.setHorsePower( Integer.parseInt( scan.next() ) );

                    break;

                case "3":

                    createType = VehicleType.AIRPLANE;
                    newVehicle = new Airplane();

                    System.out.print( "Fill in number of engines: " );

                    Airplane airplane = (Airplane) newVehicle;
                    airplane.setNumberOfEngines( Integer.parseInt( scan.next() ) );

                    break;

                case "4":

                    createType = VehicleType.MOTORCYCLE;
                    newVehicle = new MotorCycle();

                    System.out.print( "Fill in top speed: " );

                    MotorCycle mc = (MotorCycle) newVehicle;
                    mc.setTopSpeed( Integer.parseInt( scan.next() ) );

                    break;

                default:
                    System.out.println( "Unknown vehicle type..." );
                    throw new GarageException( "Vehicle type not set up properly..." );
            }

        } catch (NumberFormatException nf) {
            throw new GarageException("Did not enter a number", nf);
        }


        // Attach type for created object.
        newVehicle.setType( createType );
        newVehicle.setNumberOfRegistration( regNo );

        // Vehicle brand
        System.out.print( "Vehicle is of brand: " );
        String brand = scan.next();
        newVehicle.setBrand( brand );

        // Vehicle Model
        System.out.print( "Vehicle is of model: " );
        String model = scan.next();
        newVehicle.setModel( model );

        addToKnownVehicles( newVehicle );
        return newVehicle;

    }

    /**
     * Park vehicle in active garage.
     * Uses setupNewVehicle to create a new Vehicle or fetch existing one from knownVehicles.
     *
     * @param scan The scanner used to control the application
     */
    public void parkVehicle(Scanner scan) {
        if (activeGarage.getParkedVehicles().size() >= activeGarage.getGarageLimit()) {
            System.out.println( "Garage is currently full..." );
            return;
        }

        Vehicle parkingVehicle = null;

        try {
            parkingVehicle = setupNewVehicle( scan );
        } catch (GarageException ge){
            System.out.println("Aborted creation. Reason: " + ge.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (parkingVehicle == null) {
            System.out.println( "Vehicle invalid" );
            return;
        }

        if (parkingVehicle.isParked()) {
            System.out.println( "Vehicle is already parked somewhere." );
            return;
        }

        activeGarage.parkVehicle( parkingVehicle );
        System.out.println( parkingVehicle + " have been parked to the garage" );
    }


    /**
     * Remove vehicle from active garage and set isParked to false
     *
     * @param scan The scanner used to control the application
     */
    public void unparkVehicle(Scanner scan) {

        if (activeGarage.getParkedVehicles().size() == 0) {
            System.out.println( "Garage is empty..." );
            return;
        }

        // We reserve 0 for abort operation, and on selecting vehicle to unpark get index minus one;
        int index = 1;
        System.out.println( "[0] Cancel" );
        for (Vehicle vehicle : activeGarage.getParkedVehicles()) {
            System.out.println( "[" + index++ + "] " + vehicle );
        }

        System.out.print( "What vehicle do you want to check out? " );


        /**
         *  Initialize values here for use in final confirmation message.
         *  If any exceptions occur, we handle them inside the method and
         *  force the user to try again for a correct input.
         */

        boolean success = false;
        Optional<String> knownVehicleRef = null;
        String input = "";

        do {

            try {

                // Abort the checkout-operation on "0".
                input = scan.next();
                if (input.equals( "0" )) {
                    System.out.println( "Aborting..." );
                    return;
                }

                // Can throw IndexOutOfBoundsException and NumberFormatException.
                final Vehicle garageVehicle = activeGarage.getParkedVehicles().get( (Integer.parseInt( input ) - 1) );

                /*
                 * Lambda version
                 * Find reference in known vehicles by registration-number (filter -> predicate)
                 * unpark it, (peek -> consumer)
                 * transforms the vehicle-object into a string (toString from Vehicle) (map -> function)
                 * return the first objec
                 */
                knownVehicleRef = knownVehicles.stream()
                        .filter( e -> garageVehicle.getNumberOfRegistration().equals( e.getNumberOfRegistration() ) )
                        .peek( e -> e.unpark() )
                        .map( e -> e.toString() )
                        .findFirst();

                // Remove vehicle from current active garage.
                activeGarage.unParkVehicle( garageVehicle );
                success = true;

            } catch (NumberFormatException nfe) {
                System.out.println( input + " is not a valid option. Please try again." );
            } catch (IndexOutOfBoundsException ioobe) {
                System.out.println( "That is not possible. Please try again." );
            } catch (Exception e) {
                System.out.println( "Something else went wrong : " + e.getMessage() );
            }

        } while (!success);

        if (knownVehicleRef.isPresent()) {
            System.out.println( knownVehicleRef.get() + " have been checked out from " + activeGarage.getLocation() );
        }
    }


    /**
     * Print out all the known vehicles along with current parking status.
     */
    public void listAllKnownVehicles() {
        System.out.println( "----------------" );
        for (Vehicle vehicle : knownVehicles) {
            System.out.println( vehicle + ", parking status: " + vehicle.isParked() );
            System.out.println( "----------------" );
        }
    }

    /**
     * Selects an active garage from index in availableGarages.
     * Uses scanner to select the index after iterating over all locations.
     *
     * @param scan The scanner used to control the application
     */
    public void selectActiveGarage(Scanner scan) {
        int index = 0;
        for (Garage garage : availableGarages) {
            System.out.println( "[" + index++ + "] " + garage.getLocation() );
        }
        System.out.print( "Please select a garage to manage: " );
        try {
            Integer keyboard = Integer.parseInt( scan.next() );
            activeGarage = availableGarages.get( keyboard );
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println( "You have chosen to manage " + activeGarage.getLocation() );
    }


    /**
     * Creates a new Garage and adds it to availableGarages.
     * Makes the newly created garage the active one at method end.
     * Fill in location name and total available parking spots.
     *
     * @param scan The scanner used to control the application
     */
    public void openNewGarage(Scanner scan) {

        Garage newGarage = new Garage();

        System.out.print( "Where is your new garage located? " );
        String garageLocation = scan.next();
        newGarage.setLocation( garageLocation );

        System.out.print( "How many parking spots are there? " );
        try {
            Integer garageLimit = Integer.parseInt( scan.next() );
            newGarage.setGarageLimit( garageLimit );
        } catch (Exception e) {
            e.printStackTrace();
        }

        availableGarages.add( newGarage );
        activeGarage = newGarage;
        System.out.println( "A new garage have been opened and selected as active." );
        System.out.println( "Active garage is now : " + activeGarage.getLocation() );
    }

    /**
     * Save the knownVehicles and the garage lists to file.
     * Project-root : garage.tmp
     * Project-root : vehicle.tmp
     */
    public void saveInventory() {
        Utilities.save( availableGarages, "garage.tmp" );
        Utilities.save( knownVehicles, "vehicle.tmp" );
    }


    /**
     * Load up the knownVehicles and all the garage lists from file.
     * Project-root : garage.tmp
     * Project-root : vehicle.tmp
     */
    public void loadInventory() {
        this.availableGarages = Utilities.load( "garage.tmp" );
        this.knownVehicles = Utilities.load( "vehicle.tmp" );

        if (!availableGarages.isEmpty()) {
            this.activeGarage = availableGarages.get( 0 );
        } else {
            setUpExampleStartingGarage();
        }
    }
}
