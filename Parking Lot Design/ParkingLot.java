import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLot {
    private final String lotId;
    private final List<ParkingSpot> spots;
    private final SpotSelectionStrategy spotSelectionStrategy;
    private final FeeStrategy feeStrategy;
    private final Map<String, ParkingTicket> activeTickets;

    public ParkingLot(String lotId, List<ParkingSpot> spots, SpotSelectionStrategy spotSelectionStrategy, FeeStrategy feeStrategy) {
        this.lotId = lotId;
        this.spots = spots;
        this.spotSelectionStrategy = spotSelectionStrategy;
        this.feeStrategy = feeStrategy;
        this.activeTickets = new HashMap<>();
    }

    public ParkingTicket park(Vehicle vehicle) {
        ParkingSpot freeSpot = spotSelectionStrategy.findSpot(spots, vehicle).orElse(null);

        if (freeSpot == null) {
            throw new IllegalStateException("No spot available for " + vehicle.getType());
        }

        freeSpot.park(vehicle);
        String ticketId = UUID.randomUUID().toString();
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, freeSpot, LocalDateTime.now());
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    public ParkingTicket unpark(String ticketId) {
        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket id: " + ticketId);
        }

        ticket.getSpot().vacate();
        ticket.close(LocalDateTime.now(), feeStrategy.calculate(ticket));
        return ticket;
    }

    public String showAvailableSpots() {
        int free = 0;
        for (ParkingSpot spot : spots) {
            if (spot.isFree()) {
                free++;
            }
        }
        return "ParkingLot " + lotId + " -> Free spots: " + free + "/" + spots.size();
    }
}
