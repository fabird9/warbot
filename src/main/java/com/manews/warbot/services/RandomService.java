package com.manews.warbot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomService {
    @Value("${min.people}")
    private String minPeople;

    @Value("${max.people}")
    private String maxPeople;



    public int getRandomNumberInRange() throws RandomServiceException {
        int min = Integer.parseInt(minPeople);
        int max = Integer.parseInt(maxPeople);
        if (min >= max) {
            throw new RandomServiceException("Max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
