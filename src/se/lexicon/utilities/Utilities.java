package se.lexicon.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

    private Utilities() {} // Make Utilities constructor private.

    /**
     * Generic save method for Lists. Writes to file in project root.
     * Specify filename and which list to save.
     * <T> implements Serializable. This allows us to target the List object which
     * @param inventory
     * @param filename
     * @param <E>
     */

    public static <E> void save(List<E> inventory, String filename) {
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
     * <T> implements Serializable.
     * @param filename
     * @param <E>
     * @return
     */

    public static <E> List<E> load(String filename) {
        List<E> inventory = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filename)) {

            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                inventory = (List<E>) ois.readObject();
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
