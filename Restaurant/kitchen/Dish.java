package com.javarush.task.task27.task2712.kitchen;

public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }


    public static String allDishesToString() {
        StringBuilder dishes = new StringBuilder();
        for (Dish dish : Dish.values()) {
            dishes.append(dish.toString()).append(", ");
        }

        return dishes.toString().substring(0, dishes.length() - 2);
    }

    public int getDuration() {
        return duration;
    }
}
