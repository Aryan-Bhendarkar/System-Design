public class Refill {
    private final InkColor inkColor;
    private final double maxInk;
    private double remainingInk;

    public Refill(InkColor inkColor, double maxInk) {
        if (maxInk <= 0) {
            throw new IllegalArgumentException("maxInk should be greater than 0");
        }
        this.inkColor = inkColor;
        this.maxInk = maxInk;
        this.remainingInk = maxInk;
    }

    public InkColor getInkColor() {
        return inkColor;
    }

    public double getRemainingInk() {
        return remainingInk;
    }

    public void useInk(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Ink amount cannot be negative");
        }
        remainingInk = Math.max(0, remainingInk - amount);
    }

    @Override
    public String toString() {
        return "Refill{" +
                "inkColor=" + inkColor +
                ", maxInk=" + maxInk +
                ", remainingInk=" + remainingInk +
                '}';
    }
}
