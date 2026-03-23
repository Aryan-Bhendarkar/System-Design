public class Pen {
    private final String id;
    private final String brand;
    private final PenType penType;
    private final InkUsageStrategy inkUsageStrategy;
    private Refill refill;
    private PenState state;

    public Pen(String id, String brand, PenType penType, Refill refill, InkUsageStrategy inkUsageStrategy) {
        this.id = id;
        this.brand = brand;
        this.penType = penType;
        this.refill = refill;
        this.inkUsageStrategy = inkUsageStrategy;
        this.state = PenState.CAPPED;
    }

    public void openCap() {
        if (refill.getRemainingInk() <= 0) {
            state = PenState.OUT_OF_INK;
            System.out.println("Cannot open. Pen has no ink.");
            return;
        }
        state = PenState.OPEN;
    }

    public void closeCap() {
        if (state != PenState.OUT_OF_INK) {
            state = PenState.CAPPED;
        }
    }

    public void write(String text) {
        if (state != PenState.OPEN) {
            System.out.println("Open cap first to write.");
            return;
        }

        if (text == null || text.isBlank()) {
            System.out.println("Text is empty.");
            return;
        }

        double inkNeeded = inkUsageStrategy.calculateInkNeeded(text, penType);
        if (inkNeeded > refill.getRemainingInk()) {
            refill.useInk(refill.getRemainingInk());
            state = PenState.OUT_OF_INK;
            System.out.println("Pen ran out of ink while writing.");
            return;
        }

        refill.useInk(inkNeeded);
        if (refill.getRemainingInk() <= 0) {
            state = PenState.OUT_OF_INK;
        }
    }

    public void replaceRefill(Refill newRefill) {
        refill = newRefill;
        state = PenState.CAPPED;
    }

    @Override
    public String toString() {
        return "Pen{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", type=" + penType +
                ", state=" + state +
                ", ink=" + String.format("%.2f", refill.getRemainingInk()) +
                '}';
    }
}
