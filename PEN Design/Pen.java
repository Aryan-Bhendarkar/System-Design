

public class Pen {
    private final String id;
    private final String brand;
    private final PenType penType;
    private final TipType tipType;
    private final double maxPressure;

    private Refill refill;
    private final WritingStrategy writingStrategy;
    private PenState state;

    public Pen(String id,
               String brand,
               PenType penType,
               TipType tipType,
               double maxPressure,
               Refill refill,
               WritingStrategy writingStrategy) {
        if (maxPressure <= 0) {
            throw new IllegalArgumentException("maxPressure should be > 0");
        }
        this.id = id;
        this.brand = brand;
        this.penType = penType;
        this.tipType = tipType;
        this.maxPressure = maxPressure;
        this.refill = refill;
        this.writingStrategy = writingStrategy;
        this.state = refill.hasInk() ? PenState.CAPPED : PenState.OUT_OF_INK;
    }

    public void openCap() {
        if (state == PenState.OUT_OF_INK) {
            throw new PenOperationException("Cannot open cap. Pen is out of ink");
        }
        state = PenState.READY;
    }

    public void closeCap() {
        if (state != PenState.OUT_OF_INK) {
            state = PenState.CAPPED;
        }
    }

    public void replaceRefill(Refill refill) {
        this.refill = refill;
        this.state = PenState.CAPPED;
    }

    public void write(String text, double pressure) {
        validateWriteRequest(text, pressure);
        ensureReadyToWrite();

        double inkToUse = writingStrategy.estimateInkUsage(text, pressure, tipType);

        if (inkToUse > refill.getRemainingInk()) {
            state = PenState.OUT_OF_INK;
            throw new PenOperationException("Pen ran out of ink while writing");
        }

        refill.consume(inkToUse);

        if (!refill.hasInk()) {
            state = PenState.OUT_OF_INK;
        }
    }

    public PenState getState() {
        return state;
    }

    public double getRemainingInk() {
        return refill.getRemainingInk();
    }

    public String getSummary() {
        return "Pen{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", type=" + penType +
                ", tip=" + tipType +
                ", ink=" + refill.getInkColor() +
                ", state=" + state +
                ", remainingInk=" + String.format("%.2f", refill.getRemainingInk()) +
                '}';
    }

    private void validateWriteRequest(String text, double pressure) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be null/blank");
        }
        if (pressure <= 0 || pressure > maxPressure) {
            throw new IllegalArgumentException("Pressure should be in range (0, " + maxPressure + "]");
        }
    }

    private void ensureReadyToWrite() {
        if (state == PenState.CAPPED) {
            throw new PenOperationException("Cannot write. Open cap first");
        }
        if (state == PenState.OUT_OF_INK || !refill.hasInk()) {
            state = PenState.OUT_OF_INK;
            throw new PenOperationException("Cannot write. Pen is out of ink");
        }
    }
}
