import java.util.List;
import java.util.Optional;

public interface SpotSelectionStrategy {
    Optional<ParkingSpot> findSpot(List<ParkingSpot> spots, Vehicle vehicle);
}
