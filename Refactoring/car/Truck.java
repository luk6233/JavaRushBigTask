package JavaRushBigTasks.Refactoring.car;

public class Truck extends Car {

    public Truck(int numberOfPassengers) {
        super(0, numberOfPassengers);
    }

    @Override
    public int getMaxSpeed() {
        return MAX_TRUCK_SPEED;
    }

}
