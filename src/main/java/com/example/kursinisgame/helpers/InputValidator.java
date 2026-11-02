package com.example.kursinisgame.helpers;

import java.util.Optional;

public class InputValidator {

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Optional<SellInputError> validateSellQuantity(String input, int ownedQuantity) {
        if (input.isEmpty()) {
            return Optional.of(SellInputError.EMPTY);
        }
        if (!isInteger(input)) {
            return Optional.of(SellInputError.NOT_A_NUMBER);
        }
        if (Integer.parseInt(input) > ownedQuantity) {
            return Optional.of(SellInputError.NOT_ENOUGH_ASSETS);
        }
        return Optional.empty();
    }

}
