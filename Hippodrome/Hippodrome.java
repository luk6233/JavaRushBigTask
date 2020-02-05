package JavaRushBigTasks.Hippodrome;

import java.util.ArrayList;
import java.util.List;

public class Hippodrome {
    private List<Horse> horses;
    public static Hippodrome game;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void move() {
        for (Horse horse : horses) {
            horse.move();
        }
    }

    public void print() {
        for (Horse horse : horses) {
            horse.print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public void run() {
        for (int i = 0; i < 100; i++){
            move();
            print();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Horse getWinner() {
        Horse winnerHorse = horses.get(0);
        for (int i = 0; i < horses.size() - 1; i++) {
            if (horses.get(i).getDistance() < horses.get(i + 1).getDistance()) winnerHorse = horses.get(i + 1);
        }

        return winnerHorse;
    }

    public void printWinner() {
        System.out.printf("Winner is %s!", getWinner().getName());
    }

    public static void main(String[] args) {
        Horse horse1 = new Horse("Bill", 3, 0);
        Horse horse2 = new Horse("Bob", 3, 0);
        Horse horse3 = new Horse("Brown", 3, 0);
        List<Horse> horses = new ArrayList<Horse>();
        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);
        game = new Hippodrome(horses);
        game.run();
        game.printWinner();

    }
}
