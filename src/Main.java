import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.loadStateFromFile("coffee_machine_status.txt");

        CoffeeMachineUI coffeeMachineUI = new CoffeeMachineUI(coffeeMachine);
        coffeeMachineUI.start();

        coffeeMachine.saveStateToFile("coffee_machine_status.txt");
    }
}
