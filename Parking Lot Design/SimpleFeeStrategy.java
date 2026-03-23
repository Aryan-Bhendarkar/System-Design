import java.time.Duration;
import java.time.LocalDateTime;

public class SimpleFeeStrategy implements FeeStrategy {
    @Override
    public double calculate(ParkingTicket ticket) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = Math.max(1, Duration.between(ticket.getEntryTime(), now).toMinutes());
        long hours = (minutes + 59) / 60;

        switch (ticket.getVehicle().getType()) {
            case BIKE:
                return hours * 20;
            case CAR:
                return hours * 40;
            case TRUCK:
                return hours * 70;
            default:
                return hours * 40;
        }
    }
}
