package com.example.kursinisgame.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Asset {

    private static final double ASSET_TAX = 0.05;

    protected double assetPrice;
    protected double assetTax;
    private PriceModel model;
    private List<Double> priceHistory = new ArrayList<>();

    protected Asset(double assetPrice, Models model, double assetTax) {
        this.assetPrice = assetPrice;
        this.model = PriceModelFactory.createModel(model);
        this.priceHistory.add(assetPrice);
        this.assetTax = assetTax;
    }

    public void changePrice(){
        assetPrice = model.changePrice(assetPrice);
        priceHistory.add(assetPrice);
    }

    public double getSMA() {
        double sum = 0;
        for (Double aDouble : priceHistory) { sum += aDouble; }
        return Math.round((sum / priceHistory.size()) * 100.0) / 100.0;
    }

    public double getAssetPriceWithTax() {
        return assetPrice * (1 + assetTax);
    }

    public double getAssetPrice() {
        return assetPrice;
    }

    public double getAssetTax() {
        return assetTax;
    }

    public List<Double> getPriceHistory() {
        return priceHistory;
    }
}
