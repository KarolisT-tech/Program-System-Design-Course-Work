package com.example.kursinisgame.models;

import java.util.Random;

public class RandomPriceModel implements PriceModel {

    private static final int MAX_CHANGE_PERCENT = 10;
    private static final int MIN_CHANGE_PERCENT = 1;
    private static final double PERCENT_DIVISOR = 100.0;

    @Override
    public double changePrice(double price) {
        Random rand = new Random();
        double change = (MIN_CHANGE_PERCENT + rand.nextInt(MAX_CHANGE_PERCENT)) / PERCENT_DIVISOR;
        return rand.nextBoolean() ? price + price * change : price - price * change;
    }
}
