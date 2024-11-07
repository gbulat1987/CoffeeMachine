import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    private static final String FILENAME = "doc/coffee_machine_status.txt";

    public CoffeeMachine() {
        loadStateFromFile(FILENAME);
        // Initialize default values if the file doesn't exist or is invalid
        if (water == 0 && milk == 0 && beans == 0 && cups == 0 && money == 0) {
            initializeDefaultValues();
        }
    }

    void loadStateFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return; // Skip loading if the file does not exist
        }

        try (Scanner scanner = new Scanner(file)) {
            // Load machine status
            if (scanner.hasNextLine()) {

                String[] status = scanner.nextLine().split("; ");
                water = Integer.parseInt(status[0].split(": ")[1]);
                milk = Integer.parseInt(status[1].split(": ")[1]);
                beans = Integer.parseInt(status[2].split(": ")[1]);
                cups = Integer.parseInt(status[3].split(": ")[1]);
                money = Integer.parseInt(status[4].split(": ")[1]);
            }

            // Skip the next line (admin credentials)
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Skip the coffee types part
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip coffee types
            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not load coffee machine state from file.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in the coffee machine state file.");
        }
    }

    public void saveStateToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            // Save machine status
            writer.println("water: " + water + "; milk: " + milk + "; beans: " + beans + "; cups: " + cups + "; money: " + money);
            writer.println("Username: admin; Password: admin123");
            writer.println();
            // Skip the coffee types part
            // Save coffee types directly
            writer.println("Espresso, 250, 0, 16, 4");
            writer.println("Latte, 350, 75, 20, 7");
            writer.println("Cappuccino, 200, 100, 12, 6");

            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.out.println("Could not save coffee machine state to file.");
        }
    }

    private void initializeDefaultValues() {
        this.water = 700;
        this.milk = 1390;
        this.beans = 1080;
        this.cups = 107;
        this.money = 564;
    }

    public void printState() {
        System.out.println("CoffeeMachine{ water=" + water + ", milk=" + milk + ", coffeeBeans=" + beans + ", cups=" + cups + ", money=" + money + "}");
    }

    private boolean checkResources(CoffeeCupType type) {
        if (water < type.getWaterNeeded()) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (milk < type.getMilkNeeded()) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (beans < type.getBeansNeeded()) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
        if (cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }
        return true;
    }

    public void makeCoffee(CoffeeCupType type) {
        if (checkResources(type)) {
            water -= type.getWaterNeeded();
            milk -= type.getMilkNeeded();
            beans -= type.getBeansNeeded();
            cups--;
            money += type.getCost();
            System.out.println("I have enough resources, making you " + type.getName());
            saveStateToFile(FILENAME); // Save state after making coffee
        }
    }

    public void fill(int water, int milk, int beans, int cups) {
        this.water += water;
        this.milk += milk;
        this.beans += beans;
        this.cups += cups;
        saveStateToFile(FILENAME); // Save state after filling
    }

    public void take() {
        System.out.println("I gave you $" + money);
        money = 0;
        saveStateToFile(FILENAME); // Save state after taking money
    }
}
