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

    private CoffeeCupType[] coffeeTypes; // Niz koji sadrži vrste kave
    private int coffeeCount; // Broj trenutno pohranjenih vrsta kave

    // Konstruktor koji inicijalizira s default vrijednostima
    public CoffeeMachine() {
        this.coffeeTypes = new CoffeeCupType[100];
        this.coffeeCount = 0;
        initializeDefaultValues();
    }

    // Učitava stanje iz datoteke
    public void loadStateFromFile(String filename) {

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

    // Postavlja početne vrijednosti stroja
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

    // Vraća niza vrsta kave
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
