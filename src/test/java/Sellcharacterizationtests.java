import com.example.kursinisgame.models.*;

import com.example.kursinisgame.helpers.InputValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Characterization tests written before refactoring AssetSellerForm.Sell.
 * Sell itself cannot be called without JavaFX, so these tests pin down the current
 * behaviour of the non-UI code it relies on: quantity validation and the game end check.
 */
class SellCharacterizationTests {

    // ---------- InputValidator.isInteger (second check in Sell) ----------

    @Test
    void emptyQuantityIsNotInteger() {
        assertFalse(InputValidator.isInteger(""));
    }

    @Test
    void textQuantityIsNotInteger() {
        assertFalse(InputValidator.isInteger("abc"));
    }

    @Test
    void decimalQuantityIsNotInteger() {
        assertFalse(InputValidator.isInteger("2.5"));
    }

    @Test
    void quantityWithSpacesIsNotInteger() {
        assertFalse(InputValidator.isInteger(" 3"));
    }

    @Test
    void positiveQuantityIsInteger() {
        assertTrue(InputValidator.isInteger("3"));
    }

    // Current behaviour, not necessarily correct: zero and negative quantities pass validation.

    @Test
    void zeroQuantityIsAcceptedAsInteger() {
        assertTrue(InputValidator.isInteger("0"));
    }

    @Test
    void negativeQuantityIsAcceptedAsInteger() {
        assertTrue(InputValidator.isInteger("-2"));
    }

    // ---------- TradingGame.isGameEnd (last check in Sell) ----------

    @Test
    void gameEndsWhenBalanceAndAssetsAreZero() {
        assertTrue(gameWith(0, 0, 5).isGameEnd());
    }

    @Test
    void gameEndsWhenNoMovesLeft() {
        assertTrue(gameWith(100, 3, 0).isGameEnd());
    }

    @Test
    void gameContinuesWithBalanceAndMoves() {
        assertFalse(gameWith(100, 0, 5).isGameEnd());
    }

    @Test
    void gameContinuesWithZeroBalanceButAssets() {
        assertFalse(gameWith(0, 2, 5).isGameEnd());
    }

    // Balance is compared with == 0, so any positive remainder keeps the game going.
    @Test
    void gameContinuesWithTinyPositiveBalance() {
        assertFalse(gameWith(0.001, 0, 5).isGameEnd());
    }

    private static TradingGame gameWith(double balance, int assets, int movesLeft) {
        Player player = new Player(balance);
        player.setAssetQnt(assets);
        player.setMovesLeft(movesLeft);
        return new TradingGame(player);
    }
}