package com.example.kursinisgame.models;

public class CryptoAsset extends Asset{

    private static final double CRYPTO_ASSET_TAX = 0.02;

    protected CryptoAsset(double assetPrice, Models model) {
        super(assetPrice, model, CRYPTO_ASSET_TAX);
    }
}
