import com.example.kursinisgame.models.*;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Deterministic tests, possible only after refactoring: Random is now passed in
 * through the constructor, so each test forces one exact execution path.
 * The number of tests per model equals its cyclomatic complexity
 * (RandomPriceModel v(G) = 2, CrashPriceModel and VolatilityPriceModel v(G) = 3).
 */
class PriceModelDeterministicTests {

    private static final double PRICE = 100.0;
    private static final double DELTA = 1e-9;

    /** Random whose results are fixed by the test instead of generated. */
    private static final class ControlledRandom extends Random {
        private final double doubleValue;
        private final int intValue;
        private final boolean booleanValue;

        ControlledRandom(double doubleValue, int intValue, boolean booleanValue) {
            this.doubleValue = doubleValue;
            this.intValue = intValue;
            this.booleanValue = booleanValue;
        }

        @Override
        public double nextDouble() {
            return doubleValue;
        }

        @Override
        public int nextInt(int bound) {
            return intValue;
        }

        @Override
        public boolean nextBoolean() {
            return booleanValue;
        }
    }

    // ---------- RandomPriceModel ----------

    @Test
    void randomModelIncreasesByMaximumTenPercent() {
        RandomPriceModel model = new RandomPriceModel(new ControlledRandom(0, 9, true));
        assertEquals(110.0, model.changePrice(PRICE), DELTA);
    }

    @Test
    void randomModelDecreasesByMinimumOnePercent() {
        RandomPriceModel model = new RandomPriceModel(new ControlledRandom(0, 0, false));
        assertEquals(99.0, model.changePrice(PRICE), DELTA);
    }

    // ---------- CrashPriceModel ----------

    @Test
    void crashModelCrashesBelowFivePercentChance() {
        CrashPriceModel model = new CrashPriceModel(new ControlledRandom(0.01, 0, true));
        assertEquals(70.0, model.changePrice(PRICE), DELTA);
    }

    @Test
    void crashModelIncreasesByMaximumFivePercent() {
        CrashPriceModel model = new CrashPriceModel(new ControlledRandom(0.5, 4, true));
        assertEquals(105.0, model.changePrice(PRICE), DELTA);
    }

    @Test
    void crashModelDecreasesByMinimumOnePercent() {
        CrashPriceModel model = new CrashPriceModel(new ControlledRandom(0.5, 0, false));
        assertEquals(99.0, model.changePrice(PRICE), DELTA);
    }

    // ---------- VolatilityPriceModel ----------

    @Test
    void volatilityModelCalmIncrease() {
        VolatilityPriceModel model = new VolatilityPriceModel(new ControlledRandom(0.1, 0, true));
        assertEquals(102.0, model.changePrice(PRICE), DELTA);
    }

    @Test
    void volatilityModelSpikeIncrease() {
        VolatilityPriceModel model = new VolatilityPriceModel(new ControlledRandom(0.9, 0, true));
        assertEquals(115.0, model.changePrice(PRICE), DELTA);
    }

    @Test
    void volatilityModelCalmDecrease() {
        VolatilityPriceModel model = new VolatilityPriceModel(new ControlledRandom(0.1, 0, false));
        assertEquals(98.0, model.changePrice(PRICE), DELTA);
    }
}