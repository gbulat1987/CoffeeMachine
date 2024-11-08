
public class CoffeeCupType {
    private final int waterNeeded;
    private final int milkNeeded;
    private final int beansNeeded;
    private final int cost;
    private final String name;

    // Konstruktor koji inicijalizira sve vrijednosti
    public CoffeeCupType(int waterNeeded, int milkNeeded, int beansNeeded, int cost, String name) {
        this.waterNeeded = waterNeeded;
        this.milkNeeded = milkNeeded;
        this.beansNeeded = beansNeeded;
        this.cost = cost;
        this.name = name;
    }


    public int getWaterNeeded() {
        return waterNeeded;
    }

    public int getMilkNeeded() {
        return milkNeeded;
    }

    public int getBeansNeeded() {
        return beansNeeded;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}
