import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.loadStateFromFile("doc/coffee_machine_status.txt");

        CoffeeMachineUI coffeeMachineUI = new CoffeeMachineUI(coffeeMachine);
        coffeeMachineUI.start();

        coffeeMachine.saveStateToFile("doc/coffee_machine_status.txt");
    }
}
