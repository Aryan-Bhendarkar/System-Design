

public class BallPointWritingStrategy implements WritingStrategy {

    @Override
    public double estimateInkUsage(String text, double pressure, TipType tipType) {
        double tipFactor = switch (tipType) {
            case FINE -> 0.8;
            case MEDIUM -> 1.0;
            case BOLD -> 1.2;
        };

        double safePressure = Math.max(0.1, pressure);
        return text.length() * 0.02 * safePressure * tipFactor;
    }
}
