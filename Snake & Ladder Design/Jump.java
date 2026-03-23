public class Jump {
    private final int start;
    private final int end;
    private final JumpType type;

    public Jump(int start, int end, JumpType type) {
        validate(start, end, type);
        this.start = start;
        this.end = end;
        this.type = type;
    }

    private void validate(int start, int end, JumpType type) {
        if (start <= 0 || end <= 0) {
            throw new IllegalArgumentException("Jump positions must be positive.");
        }

        if (type == JumpType.SNAKE && start <= end) {
            throw new IllegalArgumentException("For a snake, start must be greater than end.");
        }

        if (type == JumpType.LADDER && start >= end) {
            throw new IllegalArgumentException("For a ladder, start must be less than end.");
        }
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public JumpType getType() {
        return type;
    }
}
