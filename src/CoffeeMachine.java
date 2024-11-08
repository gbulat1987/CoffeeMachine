import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    private CoffeeCupType[] coffeeTypes;
    private int coffeeCount;

    public CoffeeMachine() {
        this.coffeeTypes = new CoffeeCupType[100];
        this.coffeeCount = 0;
        initializeDefaultValues();
    }

    // Učitava stanje iz datoteke
    public void loadStateFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (scanner.hasNextLine()) {
                String[] resourceParts = scanner.nextLine().split("; ");
                for (String part : resourceParts) {
                    String[] keyValue = part.split(": ");
                    String key = keyValue[0].trim();
                    int value = Integer.parseInt(keyValue[1].trim());

                    switch (key) {
                        case "water": this.water = value; break;
                        case "milk": this.milk = value; break;
                        case "beans": this.beans = value; break;
                        case "cups": this.cups = value; break;
                        case "money": this.money = value; break;
                    }
                }
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Preskače liniju sa korisničkim imenom i lozinkom
            }

            coffeeCount = 0; // Resetuje broj kava
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] coffeeParts = line.split(",");
                if (coffeeParts.length == 5) {
                    String name = coffeeParts[0].trim();
                    int waterNeeded = Integer.parseInt(coffeeParts[1].trim());
                    int milkNeeded = Integer.parseInt(coffeeParts[2].trim());
                    int beansNeeded = Integer.parseInt(coffeeParts[3].trim());
                    int cost = Integer.parseInt(coffeeParts[4].trim());

                    addCoffeeType(new CoffeeCupType(waterNeeded, milkNeeded, beansNeeded, cost, name));
                }
            }

            System.out.println("State loaded successfully.");
        } catch (IOException e) {
            System.out.println("Could not load coffee machine state from file.");
        } catch (NumberFormatException e) {
            System.out.println("File format is incorrect.");
        }
    }

    // Sprema trenutno stanje u datoteku
    public void saveStateToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("water: " + water + "; milk: " + milk + "; beans: " + beans + "; cups: " + cups + "; money: " + money);
            writer.println("username: " + "admin" + "; password: " + "admin123");
            writer.println();
            for (int i = 0; i < coffeeCount; i++) {
                CoffeeCupType coffee = coffeeTypes[i];
                if (coffee != null) {
                    writer.println(coffee.getName() + "," + coffee.getWaterNeeded() + "," + coffee.getMilkNeeded() + "," + coffee.getBeansNeeded() + "," + coffee.getCost());
                }
            }
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.out.println("Could not save coffee machine state to file.");
        }
    }

    // Postavlja početne vrijednosti sa default vrijednostima
    private void initializeDefaultValues() {
        this.water = 700;
        this.milk = 1390;
        this.beans = 1080;
        this.cups = 107;
        this.money = 564;

        addCoffeeType(new CoffeeCupType(250, 0, 16, 4, "Espresso"));
        addCoffeeType(new CoffeeCupType(350, 75, 20, 7, "Latte"));
        addCoffeeType(new CoffeeCupType(200, 100, 12, 6, "Cappuccino"));
    }

    // Vraca niz vrsta kave
    public CoffeeCupType[] getCoffeeTypes() {
        return coffeeTypes;
    }

    // Ispisuje trenutno stanje
    public void printState() {
        System.out.println();
        System.out.println("CoffeeMachine{ water=" + water +
                ", milk=" + milk +
                ", coffeeBeans=" + beans +
                ", cups=" + cups +
                ", money=" + money + "}");
        System.out.println();
    }

    // Pravi kavu, smanjuje resurse i povećava novac ako ima dovoljno resursa
    public void makeCoffee(CoffeeCupType type) {
        if (checkResources(type)) {
            water -= type.getWaterNeeded();
            milk -= type.getMilkNeeded();
            beans -= type.getBeansNeeded();
            cups--;
            money += type.getCost();
            System.out.println("I have enough resources, making you " + type.getName());
        }
    }

    // Provjerava ima li dovoljno resursa za određenu vrstu kave
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
            System.out.println("Sorry, not enough cups!");
            return false;
        }
        return true;
    }

    // Dodaje nove resurse
    public void fill(int water, int milk, int beans, int cups) {
        this.water += water;
        this.milk += milk;
        this.beans += beans;
        this.cups += cups;
    }

    // Metoda za uzimanje novca
    public void take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    // Dodaje novu vrstu kave u niz
    public boolean addCoffeeType(CoffeeCupType newCoffee) {
        if (coffeeCount < coffeeTypes.length) {
            coffeeTypes[coffeeCount++] = newCoffee;
            return true;
        }
        return false;
    }

    // Omogućuje unos podataka za novu kavu i dodavanje iste u niz
    public void handleAddNewCoffee(Scanner scanner) {
        System.out.println("Enter the name of the new coffee:");
        String name = scanner.next();

        System.out.println("Enter the amount of water (in ml) needed:");
        int water = scanner.nextInt();

        System.out.println("Enter the amount of milk (in ml) needed:");
        int milk = scanner.nextInt();

        System.out.println("Enter the amount of beans (in grams) needed:");
        int beans = scanner.nextInt();

        System.out.println("Enter the cost of the coffee:");
        int cost = scanner.nextInt();

        CoffeeCupType newCoffee = new CoffeeCupType(water, milk, beans, cost, name);
        if (addCoffeeType(newCoffee)) {
            System.out.println("New coffee added: " + newCoffee.getName());
        } else {
            System.out.println("Unable to add new coffee. The list is full.");
        }
    }
}
