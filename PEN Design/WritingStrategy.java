

public interface WritingStrategy {
    double estimateInkUsage(String text, double pressure, TipType tipType);
}
