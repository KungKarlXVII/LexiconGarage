package se.lexicon.ui;

import se.lexicon.controller.GarageHandler;
import se.lexicon.utilities.Utilities;

import java.util.Scanner;

public class UI {

    private GarageHandler handler = new GarageHandler();


    /**
     * Prints the available options to console by using StringBuilder and '\n' newline character.
     */
    public void printStartMenu() {

        StringBuilder sb = new StringBuilder("Start Menu - \n");
        sb.append("1. Show parked vehicles in active garage\n");
        sb.append("2. Park vehicle in active garage\n");
        sb.append("3. Unpark vehicle from active garage\n");
        sb.append("4. --- \n");
        sb.append("5. --- \n");
        sb.append("6. --- \n");
        sb.append("7. List all known vehicles \n");
        sb.append("8. Select active garage \n");
        sb.append("9. Open new Garage \n");
        sb.append("0. Exit Program\n");

        System.out.println(sb.toString());
    }


    /**
     * Starts the applications UI with a Scanner that receives all user input.
     * EXITS on GarageHandler-thrown Exceptions or when chosen from menu.
     * Exits gracefully and saves current inventory.
     */
    public void start() {

        // Program-loop
        boolean isRunning = true;

        // Scanner implements AutoClosable interface so we can use try-with-resources to automatically
        // handle the closing of the scanner.

        try (Scanner scanner = new Scanner(System.in)) {

            handler.loadInventory();

            do {

                printStartMenu();
                String keyboard = scanner.next();

                switch (keyboard) {

                    case "1":
                        handler.showGarageInventory();
                        break;

                    case "2":
                        handler.parkVehicle( scanner );
                        break;

                    case "3":
                        handler.unparkVehicle( scanner );
                        break;


                    case "7":
                        System.out.println( "List all known vehicles" );
                        handler.listAllKnownVehicles();
                        break;

                    case "8":
                        System.out.println( "Select active garage" );
                        handler.selectActiveGarage( scanner );
                        break;

                    case "9":
                        System.out.println( "Opening up new garage location..." );
                        handler.openNewGarage( scanner );
                        break;

                    case "0":
                        System.out.println( "Exiting program..." );
                        isRunning = false;
                        break;

                    default:
                        System.out.println( keyboard + " is an invalid menu option. \nPlease try again..." );
                        break;
                }

            } while (isRunning);

        // Additional more specific exceptions should be caught here for more specific error messaging.

        } catch (Exception e) {
            System.out.println("Error: Something went wrong... " + e.getMessage() + ".");

        } finally {
            handler.saveInventory();
            System.out.println("Saving inventory... Thank you for using LexiconGarage 1.0");

        }

    }

}
