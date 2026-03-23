

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = createSampleParkingLot();

        try {
            System.out.println(parkingLot.displayAvailability());

            Vehicle car = new Vehicle("DL-01-AB-1234", VehicleType.CAR);
            Vehicle bike = new Vehicle("DL-05-CD-7777", VehicleType.BIKE);
            Vehicle truck = new Vehicle("HR-10-TR-9090", VehicleType.TRUCK);

            ParkingTicket carTicket = parkingLot.parkVehicle(car);
            ParkingTicket bikeTicket = parkingLot.parkVehicle(bike);
            ParkingTicket truckTicket = parkingLot.parkVehicle(truck);

            System.out.println("Tickets Generated:");
            System.out.println(carTicket);
            System.out.println(bikeTicket);
            System.out.println(truckTicket);

            System.out.println();
            System.out.println(parkingLot.displayAvailability());

            ParkingTicket closedCarTicket = parkingLot.unparkVehicle(carTicket.getTicketId());
            System.out.println("Closed Ticket: " + closedCarTicket);

            System.out.println();
            System.out.println(parkingLot.displayAvailability());
        } catch (ParkingLotException ex) {
            System.err.println("Parking lot operation failed: " + ex.getMessage());
        }
    }

    private static ParkingLot createSampleParkingLot() {
        List<ParkingSpot> floor1Spots = List.of(
            new ParkingSpot("F1-C1", 1, SpotType.COMPACT),
            new ParkingSpot("F1-C2", 1, SpotType.COMPACT),
            new ParkingSpot("F1-B1", 1, SpotType.BIKE),
            new ParkingSpot("F1-L1", 1, SpotType.LARGE),
            new ParkingSpot("F1-E1", 1, SpotType.ELECTRIC)
        );

        List<ParkingSpot> floor2Spots = List.of(
            new ParkingSpot("F2-C1", 2, SpotType.COMPACT),
            new ParkingSpot("F2-B1", 2, SpotType.BIKE),
            new ParkingSpot("F2-L1", 2, SpotType.LARGE),
            new ParkingSpot("F2-E1", 2, SpotType.ELECTRIC)
        );

        ParkingFloor floor1 = new ParkingFloor(1, floor1Spots);
        ParkingFloor floor2 = new ParkingFloor(2, floor2Spots);

        return new ParkingLot(
            "PL-DELHI-001",
            List.of(floor1, floor2),
            new NearestAvailableParkingStrategy(),
            new HourlyFeeStrategy()
        );
    }
}
