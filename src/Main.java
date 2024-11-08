// Glavna klasa koja pokreće program za CoffeeMachine
public class Main {
    public static void main(String[] args) {
        // Kreira novu instancu CoffeeMachine i učitava stanje iz datoteke
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.loadStateFromFile("doc/coffee_machine_status.txt");

        // Inicijalizira korisničko sučelje i pokreće ga
        CoffeeMachineUI coffeeMachineUI = new CoffeeMachineUI(coffeeMachine);
        coffeeMachineUI.start();

        // Sprema trenutno stanje u datoteku nakon završetka rada
        coffeeMachine.saveStateToFile("doc/coffee_machine_status.txt");
    }
}
