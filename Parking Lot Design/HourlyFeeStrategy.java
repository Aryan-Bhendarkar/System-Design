

import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyFeeStrategy implements FeeStrategy {

    @Override
    public double calculateFee(ParkingTicket ticket) {
        LocalDateTime endTime = LocalDateTime.now();
        long minutes = Duration.between(ticket.getEntryTime(), endTime).toMinutes();
        long hours = Math.max(1, (minutes + 59) / 60);

        return switch (ticket.getVehicle().getVehicleType()) {
            case BIKE -> hours * 20.0;
            case CAR -> hours * 40.0;
            case ELECTRIC_CAR -> hours * 50.0;
            case TRUCK -> hours * 80.0;
        };
    }
}
