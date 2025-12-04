package com.example.kursinisgame.models;

public class AssetFactory {

    public static Asset createAsset(double price, Models model, AssetType type) {

        return switch (type) {
            case STOCK -> new StockAsset(price, model);
            case CRYPTO -> new CryptoAsset(price, model);
        };
    }
}
