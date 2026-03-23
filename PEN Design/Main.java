

public class Main {
    public static void main(String[] args) {
        Pen pen = PenFactory.createBallPen("P-101", "Reynolds", TipType.MEDIUM, InkColor.BLUE);

        System.out.println("Initial: " + pen.getSummary());

        pen.openCap();
        pen.write("Low level design interview question", 1.2);
        System.out.println("After write 1: " + pen.getSummary());

        pen.write("Design should be clean and extensible", 1.0);
        System.out.println("After write 2: " + pen.getSummary());

        pen.closeCap();
        System.out.println("After close cap: " + pen.getSummary());

        // Simulate refill replacement when needed.
        pen.replaceRefill(new Refill("Reynolds-NewRefill", InkColor.BLUE, 12.0));
        System.out.println("After refill replacement: " + pen.getSummary());
    }
}
