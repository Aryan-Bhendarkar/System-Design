public class SimpleInkUsageStrategy implements InkUsageStrategy {
    @Override
    public double calculateInkNeeded(String text, PenType penType) {
        double base = text.length() * 0.05;

        if (penType == PenType.GEL) {
            return base * 1.2;
        }
        return base;
    }
}