import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<ParkingSpot> spots = new ArrayList<>();
        spots.add(new ParkingSpot("S1", SpotType.BIKE));
        spots.add(new ParkingSpot("S2", SpotType.CAR));
        spots.add(new ParkingSpot("S3", SpotType.TRUCK));
        spots.add(new ParkingSpot("S4", SpotType.CAR));

        SpotSelectionStrategy spotSelectionStrategy = new FirstFitSpotSelectionStrategy();
        FeeStrategy feeStrategy = new SimpleFeeStrategy();
        ParkingLot lot = new ParkingLot("PL-1", spots, spotSelectionStrategy, feeStrategy);

        System.out.println(lot.showAvailableSpots());

        ParkingTicket t1 = lot.park(new Vehicle("DL1", VehicleType.CAR));
        ParkingTicket t2 = lot.park(new Vehicle("DL2", VehicleType.BIKE));

        System.out.println("After parking:");
        System.out.println(lot.showAvailableSpots());
        System.out.println(t1);
        System.out.println(t2);

        lot.unpark(t1.getTicketId());

        System.out.println("After unparking one car:");
        System.out.println(lot.showAvailableSpots());
    }
}
