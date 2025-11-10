package com.example.kursinisgame.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Asset {

    public double assetPrice;
    public double assetPriceWithTax;
    public double assetTax = 0.05;
    public Models model;
    public List<Double> priceHistory = new ArrayList<>();

    public Asset(double assetPrice, Models model) {
        this.assetPrice = assetPrice;
        this.model = model;
        this.assetPriceWithTax = assetPrice + (assetPrice * assetTax);
        this.priceHistory.add(assetPrice);
    }

    public void changePrice(){

        Random rand = new Random();

        switch(model){

            case RANDOM -> {
                // Random increase or decrease by 1-10%
                double changePercent = (1 + rand.nextInt(10)) / 100.0;
                boolean increase = rand.nextBoolean();

                if (increase) { assetPrice += assetPrice * changePercent;}
                else { assetPrice -= assetPrice * changePercent;}
            }

            case CRASH -> {
                // 5% chance of a 30% crash or 1-5% increase or decrease
                if (rand.nextDouble() < 0.05) { assetPrice *= 0.7;}
                else {
                    double changePercent = (1 + rand.nextInt(5)) / 100.0;
                    boolean increase = rand.nextBoolean();

                    if (increase) {
                        assetPrice += assetPrice * changePercent;
                    } else {
                        assetPrice -= assetPrice * changePercent;
                    }
                }
            }

            case VOLATILITY -> {
                // 80% calm 2% increase or decrease | 20% spike 15% increase or decrease
                double volatility = rand.nextDouble() < 0.8 ? 0.02 : 0.15;
                boolean increase = rand.nextBoolean();

                if (increase) {
                    assetPrice += assetPrice * volatility;
                } else {
                    assetPrice -= assetPrice * volatility;
                }
            }

        }

        priceHistory.add(assetPrice);
        assetPriceWithTax = assetPrice + (assetPrice * assetTax);
    }

    public double getSMA() {
        double sum = 0;
        for (int i = 0; i < priceHistory.size(); i++) {
            sum += priceHistory.get(i);
        }
        return Math.round((sum / priceHistory.size()) * 100.0) / 100.0; // rounded to 2 decimals
    }

    public double getAssetPriceWithTax() {
        return assetPriceWithTax;
    }

    public double getAssetPrice() {
        return assetPrice;
    }

    public double getAssetTax() {
        return assetTax;
    }
}
