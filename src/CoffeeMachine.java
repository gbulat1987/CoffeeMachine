import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;
    private List<CoffeeCupType> coffeeTypes; // List to store coffee types

    private static final String DIRECTORY_PATH = "doc";
    private static final String FILENAME = DIRECTORY_PATH + "/coffee_machine_status.txt";

    public CoffeeMachine() {
        coffeeTypes = new ArrayList<>();
        loadStateFromFile(FILENAME); // Load saved state on startup
    }

    // Load machine state from file
    public void loadStateFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            // No file found, use default values and add default coffee types
            initializeDefaultValues();
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                String[] status = scanner.nextLine().split("; ");
                water = Integer.parseInt(status[0]);
                milk = Integer.parseInt(status[1]);
                beans = Integer.parseInt(status[2]);
                cups = Integer.parseInt(status[3]);
                money = Integer.parseInt(status[4]);
            }

            // Read admin credentials (for demonstration purposes)
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip admin credentials
            }

            // Load coffee types
            coffeeTypes.clear();
            while (scanner.hasNextLine()) {
                String[] coffeeDetails = scanner.nextLine().split("; ");
                String name = coffeeDetails[0];
                int waterNeeded = Integer.parseInt(coffeeDetails[1]);
                int milkNeeded = Integer.parseInt(coffeeDetails[2]);
                int beansNeeded = Integer.parseInt(coffeeDetails[3]);
                int cost = Integer.parseInt(coffeeDetails[4]);
                coffeeTypes.add(new CoffeeCupType(waterNeeded, milkNeeded, beansNeeded, cost, name));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not load coffee machine state from file.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in the coffee machine state file.");
        }
    }

    // Save machine state to file
    public void saveStateToFile(String filename) {
        // Ensure the directory exists
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            // Save machine status
            writer.println(water + "; " + milk + "; " + beans + "; " + cups + "; " + money);
            writer.println("admin; admin12345"); // Save admin credentials

            // Save coffee types in specified format
            for (CoffeeCupType type : coffeeTypes) {
                writer.println(type.getName() + "; " + type.getWaterNeeded() + "; " + type.getMilkNeeded() + "; " + type.getBeansNeeded() + "; " + type.getCost());
            }
        } catch (IOException e) {
            System.out.println("Could not save coffee machine state to file.");
        }
    }

    private void initializeDefaultValues() {
        // Set default machine values
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.cups = 9;
        this.money = 550;

        // Add default coffee types
        coffeeTypes.add(new CoffeeCupType(250, 0, 16, 4, "Espresso"));
        coffeeTypes.add(new CoffeeCupType(350, 75, 20, 7, "Latte"));
        coffeeTypes.add(new CoffeeCupType(200, 100, 12, 6, "Cappuccino"));
    }

    // Display machine state
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
            saveStateToFile(FILENAME);
        }
    }

    public void fill(int water, int milk, int beans, int cups) {
        this.water += water;
        this.milk += milk;
        this.beans += beans;
        this.cups += cups;
        saveStateToFile(FILENAME);
    }

    public void take() {
        System.out.println("I gave you $" + money);
        money = 0;
        saveStateToFile(FILENAME);
    }

    public List<CoffeeCupType> getCoffeeTypes() {
        return coffeeTypes;
    }
}
