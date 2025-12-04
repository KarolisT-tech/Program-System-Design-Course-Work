package com.example.kursinisgame.models;

public class PriceModelFactory {
    public static PriceModel createModel(Models type) {
        return switch (type) {
            case RANDOM -> new RandomPriceModel();
            case CRASH -> new CrashPriceModel();
            case VOLATILITY -> new VolatilityPriceModel();
        };
    }
}
