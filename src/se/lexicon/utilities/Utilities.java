package se.lexicon.utilities;

import se.lexicon.model.Garage;
import se.lexicon.model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

    private Utilities() {}

    public static void printStartMenu() {

        StringBuilder sb = new StringBuilder("Start Menu - \n");
        sb.append("1. Show parked vehicles in active garage\n");
        sb.append("2. Park vehicle in active garage\n");
        sb.append("3. Unpark vehicle from active garage\n");
        sb.append("4. \n");
        sb.append("5. \n");
        sb.append("6. --- \n");
        sb.append("7. List all known vehicles \n");
        sb.append("8. Select active garage \n");
        sb.append("9. Open new Garage \n");
        sb.append("0. Exit Program\n");

        System.out.println(sb.toString());

    }

    public static void saveInventory(List<Garage> inventory) {
        try {
            FileOutputStream fos = new FileOutputStream("t.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(inventory);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveKnownVehicles(List<Vehicle> inventory) {
        try {
            FileOutputStream fos = new FileOutputStream("k.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(inventory);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Garage> loadInventory() {
        List<Garage> inventory = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("t.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            inventory = (List<Garage>) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inventory;
    }

    public static List<Vehicle> loadKnownVehicles() {
        List<Vehicle> inventory = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("k.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            inventory = (List<Vehicle>) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inventory;
    }

}
