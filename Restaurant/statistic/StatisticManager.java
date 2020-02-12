package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticManager {
    private static StatisticManager instance;
    private StatisticStorage  statisticStorage = new StatisticStorage();

    private StatisticManager() {}

    public static StatisticManager getInstance() {
        if (instance == null) {
            instance = new StatisticManager();
        }

        return instance;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public Map<Date, Long> getAmountPerDays() {
        TreeMap<Date, Long> amountPerDays = new TreeMap<>(Collections.reverseOrder());

        List<EventDataRow> eventDataRowList = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);

        Calendar calendar;
        Date dateWithoutTime;

        for (EventDataRow eventDataRow : eventDataRowList) {
            VideoSelectedEventDataRow vsedr = (VideoSelectedEventDataRow) eventDataRow;
            long amount = vsedr.getAmount();

            calendar = Calendar.getInstance();
            calendar.setTime(vsedr.getDate());
            GregorianCalendar gregorianCalendar = new GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            dateWithoutTime = gregorianCalendar.getTime();

            if (amountPerDays.containsKey(dateWithoutTime)) {
                amount += amountPerDays.get(dateWithoutTime);
            }

            amountPerDays.put(dateWithoutTime, amount);

        }

        return amountPerDays;
    }

    public Map<Date, TreeMap<String, Integer>> getCookingTimePerDays() {
        TreeMap<Date, TreeMap<String, Integer>> cookingTimePerDay = new TreeMap<>(Collections.reverseOrder());

        List<EventDataRow> eventDataRowList = statisticStorage.getStorage().get(EventType.COOKED_ORDER);

        Calendar calendar;
        Date dateWithoutTime;

        for (EventDataRow eventDataRow : eventDataRowList) {

            calendar = Calendar.getInstance();
            calendar.setTime(eventDataRow.getDate());
            GregorianCalendar gregorianCalendar = new GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            dateWithoutTime = gregorianCalendar.getTime();
            String cookName = ((CookedOrderEventDataRow) eventDataRow).getCookName();
            int cookTime = eventDataRow.getTime();

            if (!cookingTimePerDay.containsKey(dateWithoutTime)) {
                TreeMap<String, Integer> newCooksTime = new TreeMap<>();
                newCooksTime.put(cookName, cookTime);
                cookingTimePerDay.put(dateWithoutTime, newCooksTime);
            }
            else if (cookingTimePerDay.get(dateWithoutTime).containsKey(cookName)){
                TreeMap<String, Integer> tmpMap = cookingTimePerDay.get(dateWithoutTime);
                Integer time = tmpMap.get(cookName) + cookTime;
                tmpMap.put(cookName, time);
            } else {
                TreeMap<String, Integer> tmpMap = cookingTimePerDay.get(dateWithoutTime);
                tmpMap.put(cookName, cookTime);
            }

        }

        return cookingTimePerDay;
    }

    private static class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>();
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }
    }

}
