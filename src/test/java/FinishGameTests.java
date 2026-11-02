import com.example.kursinisgame.models.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinishGameTests {

    private TradingGame gameWith(int assetQnt, double balance, int movesLeft) {
        Player player = new Player(balance);
        player.setAssetQnt(assetQnt);
        player.setMovesLeft(movesLeft);
        return new TradingGame(player);
    }

    @Test
    public void isLost_noAssetsAndNoBalance_returnsTrue() {
        assertTrue(gameWith(0, 0.0, 5).isLost());
    }

    @Test
    public void isLost_noAssetsButHasBalance_returnsFalse() {
        assertFalse(gameWith(0, 100.0, 5).isLost());
    }

    @Test
    public void isLost_hasAssetsButNoBalance_returnsFalse() {
        assertFalse(gameWith(5, 0.0, 5).isLost());
    }

    @Test
    public void isLost_hasAssetsAndBalance_returnsFalse() {
        assertFalse(gameWith(5, 100.0, 5).isLost());
    }

    @Test
    public void isGameEnd_noBalanceAndNoAssets_returnsTrue() {
        assertTrue(gameWith(0, 0.0, 5).isGameEnd());
    }

    @Test
    public void isGameEnd_noMovesLeft_returnsTrue() {
        assertTrue(gameWith(5, 100.0, 0).isGameEnd());
    }

    @Test
    public void isGameEnd_onlyBalanceZero_returnsFalse() {
        assertFalse(gameWith(5, 0.0, 5).isGameEnd());
    }

    @Test
    public void isGameEnd_everythingPositive_returnsFalse() {
        assertFalse(gameWith(5, 100.0, 5).isGameEnd());
    }
}
