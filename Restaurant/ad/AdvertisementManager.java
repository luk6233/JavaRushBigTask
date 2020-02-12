//package com.javarush.task.task27.task2712.ad;
//
//import com.javarush.task.task27.task2712.ConsoleHelper;
//import com.javarush.task.task27.task2712.statistic.StatisticManager;
//import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class AdvertisementManager {
//    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
//    private int timeSeconds;
//    private List<Advertisement> bestSet;
//    private long bestAmount;
//    private int bestDuration = Integer.MAX_VALUE;
//
//    public AdvertisementManager(int timeSeconds) {
//        this.timeSeconds = timeSeconds;
//    }
//
//    public void processVideos() {
//
//        List<Advertisement> advertisements= new ArrayList<>();
//        for (Advertisement advertisement : storage.list()) {
//            if (advertisement.getHits() > 0) {
//                advertisements.add(advertisement);
//            }
//
//        }
//
//        if (advertisements.isEmpty()) {
//            throw new NoVideoAvailableException();
//        }
//
//        makeAllSets(advertisements);
//
//        bestSet = bestSet.stream().sorted(
//                Comparator.comparingLong(Advertisement::getAmountPerOneDisplaying).reversed().
//                        thenComparingLong(a->a.getAmountPerOneDisplaying()/a.getDuration())).
//                collect(Collectors.toList());
//
//
////        Collections.sort(bestSet, new Comparator<Advertisement>() {
////            @Override
////            public int compare(Advertisement o1, Advertisement o2) {
////                if (o1.getAmountPerOneDisplaying() > o2.getAmountPerOneDisplaying()) {
////                    return -1;
////                } else if (o1.getAmountPerOneDisplaying() < o2.getAmountPerOneDisplaying()) {
////                    return 1;
////                } else {
////                    if (o1.getAmountPerOneSecondDisplaying() < o2.getAmountPerOneSecondDisplaying()) {
////                        return -1;
////                    } else if (o1.getAmountPerOneSecondDisplaying() > o2.getAmountPerOneSecondDisplaying()) {
////                        return 1;
////                    } else {
////                        return 0;
////                    }
////
////                }
////            }
////        });
//
//        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestSet, bestAmount, bestDuration));
//
//        for (Advertisement advertisement : bestSet) {
//            ConsoleHelper.writeMessage(advertisement.toString());
//            advertisement.revalidate();
//        }
//
//    }
//
//    public int calcDurationForSet(List<Advertisement> advertisements) {
////        int duration = 0;
////        for (Advertisement advertisement : advertisements) {
////            duration += advertisement.getDuration();
////        }
////
////        return duration;
//
//        return advertisements.stream().mapToInt(Advertisement::getDuration).sum();
//    }
//
//    public long calcAmountForSet(List<Advertisement> advertisements) {
////        long amount = 0L;
////        for (Advertisement advertisement : advertisements) {
////            amount += advertisement.getAmountPerOneDisplaying();
////        }
////
////        return amount;
//
//        return advertisements.stream().mapToLong(Advertisement::getAmountPerOneDisplaying).sum();
//    }
//
//    private void checkSet(List<Advertisement> advertisements) {
//        int duration = calcDurationForSet(advertisements);
//        long amount = calcAmountForSet(advertisements);
//
//        if (bestSet == null && duration <= timeSeconds) {
//            bestSet = advertisements;
//            bestAmount = amount;
//            bestDuration = duration;
//        } else {
//            if (duration <= timeSeconds) {
//                if (amount > bestAmount) {
//                    bestSet = advertisements;
//                    bestAmount = amount;
//                    bestDuration = duration;
//                }
//
//                if (amount == bestAmount) {
//                    if (duration > bestDuration) {
//                        bestSet = advertisements;
//                        bestAmount = amount;
//                        bestDuration = duration;
//                    }
//
//                    if (duration == bestDuration && advertisements.size() < bestSet.size()) {
//                        bestSet = advertisements;
//                        bestAmount = amount;
//                        bestDuration = duration;
//                    }
//                }
//
//            }
//        }
//    }
//
//    private void makeAllSets(List<Advertisement> advertisements) {
//        if (advertisements.size() > 0) {
//            checkSet(advertisements);
//        }
//
//        for (int i = 0; i < advertisements.size(); i++) {
//            List<Advertisement> newSet = new ArrayList<>(advertisements);
//
//            newSet.remove(i);
//
//            makeAllSets(newSet);
//        }
//    }
//}

package com.javarush.task.task27.task2712.ad;


import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertisementManager {
    private int timeSeconds;
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private List<Advertisement> bestCollection = new ArrayList<>();
    private long bestPrice = 0;
    private int bestDuration = 0;
    private StatisticManager statisticManager = StatisticManager.getInstance();


    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }
    public void processVideos() {
        List<Advertisement> list = new ArrayList<>();
        for (Advertisement advertisement : storage.list()) {
            if (advertisement.getHits() > 0)
                list.add(advertisement);
        }

        recurse(list);

        if (storage.list().isEmpty()){
            NoVideoAvailableException exception = new NoVideoAvailableException();
            throw new NoVideoAvailableException();
        }

        Collections.sort(bestCollection, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return (int) (o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying());
            }
        }.thenComparing(new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return (int)((o1.getAmountPerOneDisplaying() * 1000) / o1.getDuration() - (o2.getAmountPerOneDisplaying()*1000)/o2.getDuration());
            }
        }));

        statisticManager.register(new VideoSelectedEventDataRow(bestCollection,bestPrice,bestDuration));

        for (Advertisement advertisement : bestCollection){
            ConsoleHelper.writeMessage(advertisement.getName() + " is displaying... " + advertisement.getAmountPerOneDisplaying() + ", " + (advertisement.getAmountPerOneDisplaying() * 1000) / advertisement.getDuration());

            advertisement.revalidate();
        }
    }

    private void recurse(List<Advertisement> list){

        if(!list.isEmpty()){
            int resultDuration = 0;
            long resultPrice = 0;

            for(Advertisement advertisement : list){
                resultDuration = resultDuration + advertisement.getDuration();
                resultPrice = resultPrice + advertisement.getAmountPerOneDisplaying();
            }

            if (resultDuration <= timeSeconds && bestPrice <= resultPrice) {
                if (resultPrice == bestPrice && bestDuration <= resultDuration) {
                    if (bestDuration == resultDuration){
                        if (list.size() < bestCollection.size()) {
                            bestCollection = list;
                            bestPrice = resultPrice;
                            bestDuration = resultDuration;
                        }
                    }
                    else {
                        bestCollection = list;
                        bestPrice = resultPrice;
                        bestDuration = resultDuration;
                    }
                }
                else {
                    bestCollection = list;
                    bestPrice = resultPrice;
                    bestDuration = resultDuration;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                List<Advertisement> newList = new ArrayList<>(list);
                newList.remove(i);
                recurse(newList);
            }
        }
    }
}
