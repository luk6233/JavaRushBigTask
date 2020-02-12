package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public void printAdvertisementProfit() {

        double totalAmount = 0.0;

        for (Map.Entry<Date, Long> pair : StatisticManager.getInstance().getAmountPerDays().entrySet()) {
            double amount = (double) pair.getValue() / 100;

            ConsoleHelper.writeMessage(simpleDateFormat.format(pair.getKey()) + " - " +
                    decimalFormat.format(amount).replace(",", "."));
            totalAmount += amount;
        }

       ConsoleHelper.writeMessage("Total - " + decimalFormat.format(totalAmount).replace(",","."));


    }

    public void printCookWorkloading() {
        Locale.setDefault(Locale.ENGLISH);

        for (Map.Entry<Date, TreeMap<String, Integer>> pair : StatisticManager.getInstance().getCookingTimePerDays().entrySet()) {
            ConsoleHelper.writeMessage(simpleDateFormat.format(pair.getKey()));
            for (Map.Entry<String, Integer> pair1 : pair.getValue().entrySet()) {

                int timeMin = (int) Math.ceil((double) pair1.getValue() / 60);

                if (timeMin > 0) {
                    ConsoleHelper.writeMessage(String.format("%s - %d min", pair1.getKey(), timeMin));
                }
            }
            ConsoleHelper.writeMessage("");
        }
    }

    public void printActiveVideoSet() {
        
        List<Advertisement> activeVideoSet = StatisticAdvertisementManager.getInstance().getActiveVideoSet();

        Collections.sort(activeVideoSet, Comparator.comparing(Advertisement::getName));

        activeVideoSet.forEach(advertisement -> ConsoleHelper.writeMessage(
                String.format("%s - %d", advertisement.getName(), advertisement.getHits())
        ));

    }

    public void printArchivedVideoSet() {
        List<Advertisement> archiveVideoSet = StatisticAdvertisementManager.getInstance().getArchiveVideoSet();

        Collections.sort(archiveVideoSet, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        archiveVideoSet.forEach(advertisement -> ConsoleHelper.writeMessage(advertisement.getName()));
    }

}
