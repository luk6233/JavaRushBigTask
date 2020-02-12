package com.javarush.task.task27.task2712.ad;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private final AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static StatisticAdvertisementManager getInstance() {
        if (instance == null) {
            instance = new StatisticAdvertisementManager();
        }

        return instance;
    }

    public List<Advertisement> getActiveVideoSet() {

        return advertisementStorage.list().stream().filter(
                advertisement -> advertisement.getHits() > 0).collect(Collectors.toList());
    }

    public List<Advertisement> getArchiveVideoSet() {

        return advertisementStorage.list().stream().filter(
                advertisement -> advertisement.getHits() == 0).collect(Collectors.toList());
    }
}
