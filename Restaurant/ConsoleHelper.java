package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> list = new ArrayList<>();

        writeMessage(Dish.allDishesToString());
        writeMessage("Select dish");
        String dish = readString();

        while (!dish.equals("exit")) {
            try {
                Dish d = Dish.valueOf(dish);
                if (Arrays.asList(Dish.values()).contains(d)) {
                    list.add(Dish.valueOf(dish));
                }
            } catch (Exception e) {
                writeMessage("There is not selected dish, please select other dish");
            }

            dish = readString();
        }

        return list;
    }
}
