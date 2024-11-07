import java.util.Scanner;

public class CoffeeMachineUI {
    private final CoffeeMachine coffeeMachine;
    private final Scanner scanner;


    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public CoffeeMachineUI(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.print("Write action \nbuy, login, exit\n");
            String action = scanner.next();

            if (action.equals("buy")) {
                handleBuy();
            } else if (action.equals("login")) {
                handleLogin();
            } else if (action.equals("exit")) {
                break;
            } else {
                System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private void handleLogin() {
        System.out.print("Enter: login\nEnter username:\n");
        String username = scanner.next();
        System.out.print("Enter password:\n");
        String password = scanner.next();
        System.out.println("Welcome, " + username + "!");
        System.out.println();



        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            handleAdminActions();
        } else {
            System.out.println("Wrong username or password");
            System.out.println();
        }
    }

    private void handleAdminActions() {
        while (true) {
            System.out.print("Write action:\n *** fill, remaining, take, exit ***\n");
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
                case "exit":
                    return;
                default:
                    System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private void handleTake() {
        coffeeMachine.take();
        System.out.println();
    }

    private void handleRemaining() {
        coffeeMachine.printState();
        System.out.println();
    }

    private void handleBuy() {
        System.out.print("Enter: buy\nWhat do you want to buy?\n1- Espresso\n2- Latte\n3- Capuccino\n");
        String choice = scanner.next();

        CoffeeCupType espresso = new CoffeeCupType(250, 0, 16, 4, "Espresso");
        CoffeeCupType latte = new CoffeeCupType(350, 75, 20, 7, "Latte");
        CoffeeCupType cappuccino = new CoffeeCupType(200, 100, 12, 6, "Cappuccino");

        switch (choice) {
            case "1":
                coffeeMachine.makeCoffee(espresso);
                break;
            case "2":
                coffeeMachine.makeCoffee(latte);
                break;
            case "3":
                coffeeMachine.makeCoffee(cappuccino);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

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
}
