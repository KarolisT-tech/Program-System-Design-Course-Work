import com.example.kursinisgame.models.CrashPriceModel;
import com.example.kursinisgame.models.Models;
import com.example.kursinisgame.models.PriceModel;
import com.example.kursinisgame.models.PriceModelFactory;
import com.example.kursinisgame.models.RandomPriceModel;
import com.example.kursinisgame.models.VolatilityPriceModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Characterization tests for the price models, written before refactoring.
 * Random is created inside changePrice(), so the exact result cannot be predicted.
 * Instead, the tests check that every result is one of the values the model can
 * produce, and that over many runs every possible outcome actually appears.
 */
public class PriceModelTests {

    private static final int ITERATIONS = 1000;
    private static final double[] PRICES = {100.0, 37.5, 12345.67};
    private static final double TOLERANCE = 1e-9;

    // ---------- RandomPriceModel: +/- 1..10 % ----------

    @Test
    public void randomModelReturnsOnlyAllowedPrices() {
        for (double price : PRICES) {
            assertOnlyAllowed(new RandomPriceModel(), price, randomAllowed(price));
        }
    }

    @Test
    public void randomModelProducesEveryAllowedOutcome() {
        assertEveryOutcomeOccurs(new RandomPriceModel(), 100.0, randomAllowed(100.0));
    }

    // ---------- CrashPriceModel: 30 % crash or +/- 1..5 % ----------

    @Test
    public void crashModelReturnsOnlyAllowedPrices() {
        for (double price : PRICES) {
            assertOnlyAllowed(new CrashPriceModel(), price, crashAllowed(price));
        }
    }

    @Test
    public void crashModelProducesEveryAllowedOutcome() {
        assertEveryOutcomeOccurs(new CrashPriceModel(), 100.0, crashAllowed(100.0));
    }

    // ---------- VolatilityPriceModel: +/- 2 % or +/- 15 % ----------

    @Test
    public void volatilityModelReturnsOnlyAllowedPrices() {
        for (double price : PRICES) {
            assertOnlyAllowed(new VolatilityPriceModel(), price, volatilityAllowed(price));
        }
    }

    @Test
    public void volatilityModelProducesEveryAllowedOutcome() {
        assertEveryOutcomeOccurs(new VolatilityPriceModel(), 100.0, volatilityAllowed(100.0));
    }

    // ---------- PriceModelFactory ----------

    @Test
    public void factoryCreatesMatchingModel() {
        assertInstanceOf(RandomPriceModel.class, PriceModelFactory.createModel(Models.RANDOM));
        assertInstanceOf(CrashPriceModel.class, PriceModelFactory.createModel(Models.CRASH));
        assertInstanceOf(VolatilityPriceModel.class, PriceModelFactory.createModel(Models.VOLATILITY));
    }

    // ---------- Allowed results for each model ----------

    private static List<Double> randomAllowed(double price) {
        List<Double> allowed = new ArrayList<>();
        for (int percent = 1; percent <= 10; percent++) {
            addUpAndDown(allowed, price, percent / 100.0);
        }
        return allowed;
    }

    private static List<Double> crashAllowed(double price) {
        List<Double> allowed = new ArrayList<>();
        allowed.add(price * 0.7);
        for (int percent = 1; percent <= 5; percent++) {
            addUpAndDown(allowed, price, percent / 100.0);
        }
        return allowed;
    }

    private static List<Double> volatilityAllowed(double price) {
        List<Double> allowed = new ArrayList<>();
        addUpAndDown(allowed, price, 0.02);
        addUpAndDown(allowed, price, 0.15);
        return allowed;
    }

    private static void addUpAndDown(List<Double> allowed, double price, double change) {
        allowed.add(price + price * change);
        allowed.add(price - price * change);
    }

    // ---------- Assertion helpers ----------

    private static void assertOnlyAllowed(PriceModel model, double price, List<Double> allowed) {
        String name = model.getClass().getSimpleName();
        for (int i = 0; i < ITERATIONS; i++) {
            double result = model.changePrice(price);
            assertTrue(indexOf(result, allowed, price) >= 0,
                    name + " returned " + result + " for price " + price
                            + ", which is not one of the allowed results");
        }
    }

    private static void assertEveryOutcomeOccurs(PriceModel model, double price, List<Double> allowed) {
        String name = model.getClass().getSimpleName();
        boolean[] seen = new boolean[allowed.size()];
        for (int i = 0; i < ITERATIONS; i++) {
            int index = indexOf(model.changePrice(price), allowed, price);
            if (index >= 0) {
                seen[index] = true;
            }
        }
        for (int i = 0; i < seen.length; i++) {
            assertTrue(seen[i], name + " never produced " + allowed.get(i)
                    + " in " + ITERATIONS + " runs");
        }
    }

    private static int indexOf(double value, List<Double> allowed, double price) {
        double tolerance = TOLERANCE * Math.max(1.0, Math.abs(price));
        for (int i = 0; i < allowed.size(); i++) {
            if (Math.abs(value - allowed.get(i)) < tolerance) {
                return i;
            }
        }
        return -1;
    }
}