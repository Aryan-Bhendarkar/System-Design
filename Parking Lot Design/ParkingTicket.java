
import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot parkingSpot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private TicketStatus status;
    private double fee;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot parkingSpot, LocalDateTime entryTime) {
        this.ticketId = Objects.requireNonNull(ticketId, "ticketId cannot be null");
        this.vehicle = Objects.requireNonNull(vehicle, "vehicle cannot be null");
        this.parkingSpot = Objects.requireNonNull(parkingSpot, "parkingSpot cannot be null");
        this.entryTime = Objects.requireNonNull(entryTime, "entryTime cannot be null");
        this.status = TicketStatus.ACTIVE;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public double getFee() {
        return fee;
    }

    public void close(LocalDateTime exitTime, double fee) {
        this.exitTime = Objects.requireNonNull(exitTime, "exitTime cannot be null");
        this.fee = fee;
        this.status = TicketStatus.PAID;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
            "ticketId='" + ticketId + '\'' +
            ", vehicle=" + vehicle +
            ", parkingSpot=" + parkingSpot.getSpotId() +
            ", entryTime=" + entryTime +
            ", exitTime=" + exitTime +
            ", status=" + status +
            ", fee=" + fee +
            '}';
    }
}
