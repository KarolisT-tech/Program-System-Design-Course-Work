package com.example.kursinisgame.models;

import java.util.Random;

public class CrashPriceModel implements PriceModel {

    private static final double CRASH_PROBABILITY = 0.05;
    private static final double CRASH_MULTIPLIER = 0.7;
    private static final int MAX_NORMAL_CHANGE_PERCENT = 5;
    private static final double PERCENT_DIVISOR = 100.0;

    @Override
    public double changePrice(double price) {
        Random rand = new Random();
        if (rand.nextDouble() < CRASH_PROBABILITY) return price * CRASH_MULTIPLIER;
        double change = (1 + rand.nextInt(MAX_NORMAL_CHANGE_PERCENT)) / PERCENT_DIVISOR;
        return rand.nextBoolean() ? price + price * change : price - price * change;
    }
}
