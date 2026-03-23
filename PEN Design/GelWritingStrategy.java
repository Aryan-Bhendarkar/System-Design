

public class GelWritingStrategy implements WritingStrategy {

    @Override
    public double estimateInkUsage(String text, double pressure, TipType tipType) {
        double tipFactor = switch (tipType) {
            case FINE -> 0.9;
            case MEDIUM -> 1.1;
            case BOLD -> 1.3;
        };

        double safePressure = Math.max(0.1, pressure);
        return text.length() * 0.025 * safePressure * tipFactor;
    }
}
