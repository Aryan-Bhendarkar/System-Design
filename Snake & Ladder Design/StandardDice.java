import java.util.Random;

public class StandardDice implements Dice {
    private final int minValue;
    private final int maxValue;
    private final Random random;

    public StandardDice(int minValue, int maxValue) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("minValue must be less than maxValue.");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.random = new Random();
    }

    @Override
    public int roll() {
        return random.nextInt(maxValue - minValue + 1) + minValue;
    }
}
