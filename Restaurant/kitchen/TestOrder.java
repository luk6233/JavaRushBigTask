package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {
    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        int count = (int) (Math.random() * Dish.values().length + 1);
        dishes = new ArrayList<>();
        while (count > 0) {
            int number = (int) (Math.random() * Dish.values().length);
            dishes.add(Dish.values()[number]);
            count--;
        }
    }
}
