package com.example.kursinisgame.models;

public class StockAsset extends Asset{

    private static final double STOCK_ASSET_TAX = 0.05;

    protected StockAsset(double assetPrice, Models model) {
        super(assetPrice, model, STOCK_ASSET_TAX);
    }
}
