package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    @Override
    public String toString() {
        if (dishes.isEmpty()) return "";

        StringBuilder listOfDishes = new StringBuilder();
        for (Dish dish : dishes) {
            listOfDishes.append(dish.toString()).append(", ");
        }

        String listDishes = listOfDishes.toString().substring(0, listOfDishes.length() - 2);

        return String.format("Your order: %s of %s, cooking time %dmin",
                listDishes, tablet.toString(), getTotalCookingTime());
    }

    public int getTotalCookingTime() {
        int totalCookingTime = 0;

        for (Dish dish : dishes) {
            totalCookingTime += dish.getDuration();
        }

        return totalCookingTime;
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    protected void initDishes() throws IOException {
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public Tablet getTablet() {
        return tablet;
    }
}
