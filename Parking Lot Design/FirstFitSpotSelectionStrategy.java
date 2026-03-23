import java.util.List;
import java.util.Optional;

public class FirstFitSpotSelectionStrategy implements SpotSelectionStrategy {
    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingSpot> spots, Vehicle vehicle) {
        for (ParkingSpot spot : spots) {
            if (spot.isFree() && spot.canFit(vehicle.getType())) {
                return Optional.of(spot);
            }
        }
        return Optional.empty();
    }
}
