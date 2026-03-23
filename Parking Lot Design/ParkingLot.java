

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ParkingLot {
    private final String parkingLotId;
    private final List<ParkingFloor> floors;
    private final ParkingStrategy parkingStrategy;
    private final FeeStrategy feeStrategy;

    private final Map<String, ParkingTicket> activeTicketsById;

    public ParkingLot(String parkingLotId, List<ParkingFloor> floors, ParkingStrategy parkingStrategy, FeeStrategy feeStrategy) {
        this.parkingLotId = Objects.requireNonNull(parkingLotId, "parkingLotId cannot be null");
        this.floors = new ArrayList<>(Objects.requireNonNull(floors, "floors cannot be null"));
        this.parkingStrategy = Objects.requireNonNull(parkingStrategy, "parkingStrategy cannot be null");
        this.feeStrategy = Objects.requireNonNull(feeStrategy, "feeStrategy cannot be null");
        this.activeTicketsById = new HashMap<>();
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public List<ParkingFloor> getFloors() {
        return Collections.unmodifiableList(floors);
    }

    public synchronized ParkingTicket parkVehicle(Vehicle vehicle) {
        List<ParkingSpot> allSpots = floors.stream()
            .flatMap(floor -> floor.getSpots().stream())
            .collect(Collectors.toList());

        ParkingSpot spot = parkingStrategy.findSpot(allSpots, vehicle)
            .orElseThrow(() -> new NoSpotAvailableException("No parking spot available for vehicle: " + vehicle.getVehicleType()));

        spot.park(vehicle);
        String ticketId = UUID.randomUUID().toString();
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, spot, LocalDateTime.now());
        activeTicketsById.put(ticketId, ticket);

        return ticket;
    }

    public synchronized ParkingTicket unparkVehicle(String ticketId) {
        ParkingTicket ticket = activeTicketsById.remove(ticketId);
        if (ticket == null) {
            throw new InvalidTicketException("Invalid or already closed ticketId: " + ticketId);
        }

        double fee = feeStrategy.calculateFee(ticket);
        ticket.close(LocalDateTime.now(), fee);
        ticket.getParkingSpot().vacate();

        return ticket;
    }

    public String displayAvailability() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParkingLot: ").append(parkingLotId).append(System.lineSeparator());

        for (ParkingFloor floor : floors) {
            long available = floor.getSpots().stream().filter(ParkingSpot::isAvailable).count();
            sb.append("Floor ").append(floor.getFloorNumber())
                .append(" -> Available spots: ").append(available)
                .append("/").append(floor.getSpots().size())
                .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
