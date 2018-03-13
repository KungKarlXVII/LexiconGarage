package se.lexicon.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

    private Utilities() {} // Make Utilities constructor private.


    /**
     * Plain prints the start menu to the UI.
     * Nothing to see here.
     *
     */
    public static void printStartMenu() {

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
     * Generic save method for Lists. Writes to file in project root.
     * Specify filename and which list to save.
     * <T> implements Serializable
     * @param inventory
     * @param filename
     * @param <T>
     */

    public static <T> void save(List<T> inventory, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(inventory);
            }
        } catch (IOException ioe) {
            System.out.println("Error saving inventory to file...");
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generic load method. Loads from file in project root.
     * <T> implements Serializable
     * @param filename
     * @param <T>
     * @return
     */

    public static <T> List<T> load(String filename) {
        List<T> inventory = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filename)) {

            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                inventory = (List<T>) ois.readObject();
            }

        } catch (FileNotFoundException fnf){
            System.out.println("Error finding inventory file " + filename);

        } catch (IOException ioe) {
            System.out.println("Error loading inventory from file...");
            ioe.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inventory;
    }

}
