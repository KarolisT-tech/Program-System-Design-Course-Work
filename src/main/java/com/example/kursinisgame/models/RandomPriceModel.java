package com.example.kursinisgame.models;

import java.util.Random;

public class RandomPriceModel implements PriceModel {

    private static final int MAX_CHANGE_PERCENT = 10;
    private static final int MIN_CHANGE_PERCENT = 1;
    private static final double PERCENT_DIVISOR = 100.0;

    private final Random random;

    public RandomPriceModel() {
        this(new Random());
    }

    public RandomPriceModel(Random random) {
        this.random = random;
    }

    @Override
    public double changePrice(double price) {
        double change = (MIN_CHANGE_PERCENT + random.nextInt(MAX_CHANGE_PERCENT)) / PERCENT_DIVISOR;
        return random.nextBoolean() ? price + price * change : price - price * change;
    }
}