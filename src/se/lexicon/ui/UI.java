package se.lexicon.ui;

import se.lexicon.controller.GarageHandler;
import se.lexicon.utilities.Utilities;

import java.util.Scanner;

public class UI {

    private GarageHandler handler = new GarageHandler();

    public void start() {

        // Program-loop
        boolean isRunning = true;

        // Try-with-resources for automatic closing of scanner
        try (Scanner scanner = new Scanner(System.in)) {

            handler.loadInventory();

            do {

                Utilities.printStartMenu();
                String keyboard = scanner.next();

                switch(keyboard) {

                    case "1":
                        handler.showGarageInventory();
                        break;

                    case "2":
                        handler.parkVehicle(scanner);
                        break;

                    case "3":
                        handler.unparkVehicle(scanner);
                        break;




                    case "7":
                        System.out.println("List all known vehicles");
                        handler.listAllKnownVehicles();
                        break;

                    case "8":
                        System.out.println("Select active garage");
                        handler.selectActiveGarage(scanner);
                        break;

                    case "9":
                        System.out.println("Opening up new garage location...");
                        handler.openNewGarage(scanner);
                        break;

                    case "0":
                        System.out.println("Exiting program...");
                        isRunning = false;
                        break;

                    default:
                        System.out.println(keyboard + " is an invalid menu option. \nPlease try again...");
                        break;
                }


            } while(isRunning);

        } catch (Exception e) {
            System.out.println("Error: Something went wrong... " + e.getMessage() + ".");
        }

        handler.saveInventory();

    }

}
