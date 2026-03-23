

public final class PenFactory {

    private PenFactory() {
    }

    public static Pen createBallPen(String id, String brand, TipType tipType, InkColor color) {
        Refill refill = new Refill(brand + "-StandardRefill", color, 12.0);
        return new Pen(id, brand, PenType.BALL, tipType, 2.0, refill, new BallPointWritingStrategy());
    }

    public static Pen createGelPen(String id, String brand, TipType tipType, InkColor color) {
        Refill refill = new Refill(brand + "-GelRefill", color, 10.0);
        return new Pen(id, brand, PenType.GEL, tipType, 1.5, refill, new GelWritingStrategy());
    }
}
