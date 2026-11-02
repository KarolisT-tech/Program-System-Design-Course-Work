package com.example.kursinisgame.models;

import java.util.Random;

public class VolatilityPriceModel implements PriceModel {

    private static final double LOW_VOLATILITY = 0.02;
    private static final double HIGH_VOLATILITY = 0.15;
    private static final double LOW_VOLATILITY_CHANCE = 0.8;

    private final Random random;

    public VolatilityPriceModel() {
        this(new Random());
    }

    public VolatilityPriceModel(Random random) {
        this.random = random;
    }

    @Override
    public double changePrice(double price) {
        double vol = random.nextDouble() < LOW_VOLATILITY_CHANCE ? LOW_VOLATILITY : HIGH_VOLATILITY;
        return random.nextBoolean() ? price + price * vol : price - price * vol;
    }
}