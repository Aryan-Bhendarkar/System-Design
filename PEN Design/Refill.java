

public class Refill {
    private final String brand;
    private final InkColor inkColor;
    private final double capacity;
    private double remainingInk;

    public Refill(String brand, InkColor inkColor, double capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be > 0");
        }
        this.brand = brand;
        this.inkColor = inkColor;
        this.capacity = capacity;
        this.remainingInk = capacity;
    }

    public String getBrand() {
        return brand;
    }

    public InkColor getInkColor() {
        return inkColor;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getRemainingInk() {
        return remainingInk;
    }

    public boolean hasInk() {
        return remainingInk > 0;
    }

    public void consume(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Ink consumption cannot be negative");
        }
        if (amount > remainingInk) {
            throw new PenOperationException("Not enough ink available in refill");
        }
        remainingInk -= amount;
    }
}
