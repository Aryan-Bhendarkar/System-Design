public class Main {
    public static void main(String[] args) {
        Refill refill = new Refill(InkColor.BLUE, 10.0);
        InkUsageStrategy inkUsageStrategy = new SimpleInkUsageStrategy();
        Pen pen = new Pen("P-101", "Reynolds", PenType.BALL, refill, inkUsageStrategy);

        System.out.println("Initial: " + pen);

        pen.openCap();
        pen.write("hello lld");
        pen.write("beginner design");
        System.out.println("After writing: " + pen);

        pen.closeCap();
        System.out.println("After closing cap: " + pen);

        pen.replaceRefill(new Refill(InkColor.BLUE, 8.0));
        System.out.println("After refill change: " + pen);
    }
}
