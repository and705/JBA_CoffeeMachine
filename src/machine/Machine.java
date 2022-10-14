package machine;

import static machine.Drink.*;

public class Machine {
    private MachineState state;
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    public Machine(int water, int milk, int coffee, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = coffee;
        this.cups = cups;
        this.money = money;

        setMainState();
    }

    public boolean isWorking() {
        return state != MachineState.OFF;
    }

    public void execute(String input) {
        switch (state) {
            case MAIN:
                setState(input);
                break;
            case BUYING:
                handleBuying(input);
                setMainState();
                break;
            case ADDING_WATER:
                water += Integer.parseInt(input);
                System.out.print("Write how many ml of milk do you want to add:\n> ");
                state = MachineState.ADDING_MILK;
                break;
            case ADDING_MILK:
                milk += Integer.parseInt(input);
                System.out.print("Write how many grams of coffee beans do you want to add:\n> ");
                state = MachineState.ADDING_BEANS;
                break;
            case ADDING_BEANS:
                beans += Integer.parseInt(input);
                System.out.print("Write how many disposable cups of coffee do you want to add:\n> ");
                state = MachineState.ADDING_CUPS;
                break;
            case ADDING_CUPS:
                cups += Integer.parseInt(input);
                setMainState();
                break;
            default:
                break;
        }
    }

    public void setState(String command) {
        switch (command) {
            case "remaining":
                printState();
                setMainState();
                break;
            case "buy":
                System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:\n> ");
                state = MachineState.BUYING;
                break;
            case "fill":
                System.out.print("Write how many ml of water do you want to add:\n> ");
                state = MachineState.ADDING_WATER;
                break;
            case "take":
                giveMoney();
                setMainState();
                break;
            case "exit":
                state = MachineState.OFF;
                break;
            default:
                System.out.println("Error input");
                setMainState();
        }
    }

    private void printState() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(beans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    private void setMainState() {
        state = MachineState.MAIN;
        System.out.print("\nWrite action (buy, fill, take, remaining, exit):\n> ");
    }

    private void handleBuying(String input) {
        Drink drink;

        switch (input) {
            case "back":
                state = MachineState.MAIN;
                return;
            case "1":
                drink = ESPRESSO;
                break;
            case "2":
                drink = LATTE;
                break;
            case "3":
                drink = CAPPUCCINO;
                break;
            default:
                System.out.println("Error, you should enter 1...3");
                return;
        }

        makeCoffee(drink);
        acceptPayment(drink.getPrice());
    }

    private void makeCoffee(Drink drink) {
        if (water < drink.getWater()) {
            System.out.println("Sorry, not enough water!");
            return;
        }

        if (milk < drink.getMilk()) {
            System.out.println("Sorry, not enough milk!");
            return;
        }

        if (beans < drink.getBeans()) {
            System.out.println("Sorry, not enough coffee bean!");
            return;
        }

        if (cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return;
        }

        water -= drink.getWater();
        milk -= drink.getMilk();
        beans -= drink.getBeans();
        cups--;

        System.out.println("I have enough resources, making you a coffee!");
    }

    private void acceptPayment(int price) {
        money += price;
    }

    private void giveMoney() {
        System.out.printf("I gave you $%d\n", money);
        money = 0;
    }
}

