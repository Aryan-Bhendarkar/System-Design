

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NearestAvailableParkingStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingSpot> spots, Vehicle vehicle) {
        return spots.stream()
            .filter(ParkingSpot::isAvailable)
            .filter(spot -> spot.canFit(vehicle))
            .sorted(Comparator.comparingInt(ParkingSpot::getFloorNumber)
                .thenComparing(ParkingSpot::getSpotId))
            .findFirst();
    }
}
