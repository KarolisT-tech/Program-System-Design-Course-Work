import com.example.kursinisgame.models.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameLogicTests {
    @Test
    public void testPriceChanges() {
        Asset asset = AssetFactory.createAsset(50, Models.RANDOM, AssetType.CRYPTO);
        double oldPrice = asset.getAssetPrice();
        asset.changePrice();
        assertNotEquals(oldPrice, asset.getAssetPrice(),
                "Price must change after calling changePrice()");
    }

    @Test
    public void testBuyReducesBalanceAndAddsAsset() {
        Player player = new Player(1000);
        Asset asset = AssetFactory.createAsset(100, Models.RANDOM, AssetType.STOCK);

        double startingBalance = player.getBalance();
        player.setBalance(player.getBalance() - asset.getAssetPriceWithTax());
        player.setAssetQnt(player.getAssetQnt() + 1);

        assertEquals(1, player.getAssetQnt());
        assertEquals(startingBalance - asset.getAssetPriceWithTax(), player.getBalance(), 0.001);
    }

    @Test
    public void testSellLogicProfitCalculation() {
        Player player = new Player(0);
        Asset asset = AssetFactory.createAsset(200, Models.RANDOM, AssetType.STOCK);

        player.setAssetQnt(2);
        double profit = 2 * asset.getAssetPrice();
        double tax = profit * asset.getAssetTax();

        player.setBalance(player.getBalance() + profit - tax);

        assertEquals(profit - tax, player.getBalance(), 0.001);
    }

    @Test
    public void testSMA() {
        Asset asset = AssetFactory.createAsset(75, Models.RANDOM, AssetType.CRYPTO);

        asset.getPriceHistory().add(200.0);
        asset.getPriceHistory().add(300.0);

        double expected = (75 + 200 + 300) / 3.0;
        expected = Math.round(expected * 100.0) / 100.0;

        assertEquals(expected, asset.getSMA(), 0.001);
    }

    @Test
    public void testGameEndsCorrectly() {
        Player player = new Player(0);
        player.setAssetQnt(0);
        player.setMovesLeft(5);

        TradingGame game = new TradingGame(player);

        assertTrue(game.isGameEnd(),
                "Game should end when balance = 0 and asset quantity = 0");
    }
}


