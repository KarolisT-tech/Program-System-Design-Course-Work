package com.example.kursinisgame.helpers;

import com.example.kursinisgame.models.Asset;
import com.example.kursinisgame.models.AssetType;
import com.example.kursinisgame.models.Models;

public class GameInputValidator {

    public static String validate(
            String balance,
            String assetPrice,
            Models model,
            AssetType assetType) {

        if (balance == null || balance.isBlank() || assetPrice == null
                            || assetPrice.isBlank() || model == null
                            || assetType == null) {

            return "MISSING_DATA";
        }

        if (!InputValidator.isDouble(balance) || !InputValidator.isDouble(assetPrice)) {

            return "WRONG_DATA";
        }

        return null;
    }
}
