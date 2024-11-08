import java.util.Scanner;

// Klasa koja upravlja korisničkim sučeljem za kavu
public class CoffeeMachineUI {
    private final CoffeeMachine coffeeMachine;
    private final Scanner scanner;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    // Konstruktor koji inicijalizira CoffeeMachineUI s instancom CoffeeMachine
    public CoffeeMachineUI(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
        this.scanner = new Scanner(System.in);
    }

    // Metoda koja pokreće glavni loop korisničkog sučelja
    public void start() {
        while (true) {
            System.out.print("Write action (buy, login, exit):\n");
            String action = scanner.next();

            if (action.equals("buy")) {
                handleBuy();
            } else if (action.equals("login")) {
                handleLogin();
            } else if (action.equals("exit")) {
                coffeeMachine.saveStateToFile("doc/coffee_machine_status.txt");
                System.out.println("Exiting program...");
                break;
            } else {
                System.out.println("Invalid action. Please try again.");
            }
        }
    }

    // Metoda za  prijavu admina
    private void handleLogin() {
        System.out.print("Enter username:\n");
        String username = scanner.next();
        System.out.print("Enter password:\n");
        String password = scanner.next();

        // Provjera korisničkog imena i lozinke
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Welcome, " + username + "!");
            handleAdminActions();
        } else {
            System.out.println("Wrong username or password");
        }
        System.out.println();
    }


    private void handleAdminActions() {
        while (true) {
            System.out.print("Write action (fill, remaining, take, add new coffee(add), exit):\n");
            String action = scanner.next();

            switch (action) {
                case "fill":
                    handleFill();
                    break;
                case "remaining":
                    handleRemaining();
                    break;
                case "take":
                    handleTake();
                    break;
                case "add":
                    handleAddNewCoffee();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid action. Please try again.");
            }
        }
    }

    // Metoda za kupovinom kave
    private void handleBuy() {
        CoffeeCupType[] coffeeTypes = coffeeMachine.getCoffeeTypes();
        System.out.println("What do you want to buy?");
        for (int i = 0; i < coffeeTypes.length; i++) {
            if (coffeeTypes[i] != null) {
                System.out.println((i + 1) + "- " + coffeeTypes[i].getName());
            }
        }
        System.out.print("Enter the number of your choice:\n");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= coffeeTypes.length && coffeeTypes[choice - 1] != null) {
            CoffeeCupType selectedCoffee = coffeeTypes[choice - 1];
            coffeeMachine.makeCoffee(selectedCoffee);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    // Metoda za dodavanje resursa
    private void handleFill() {
        System.out.print("Write how many ml of water you want to add:\n");
        int water = scanner.nextInt();
        System.out.print("Write how many ml of milk you want to add:\n");
        int milk = scanner.nextInt();
        System.out.print("Write how many grams of coffee beans you want to add:\n");
        int beans = scanner.nextInt();
        System.out.print("Write how many disposable cups you want to add:\n");
        int cups = scanner.nextInt();
        coffeeMachine.fill(water, milk, beans, cups);
    }

    // Metoda za prikaz trenutnog stanja
    private void handleRemaining() {
        coffeeMachine.printState();
    }

    // Metoda za uzimanje novca
    private void handleTake() {
        coffeeMachine.take();
    }

    // Metoda za dodavanje nove vrste kave
    private void handleAddNewCoffee() {
        coffeeMachine.handleAddNewCoffee(scanner);
    }
}
