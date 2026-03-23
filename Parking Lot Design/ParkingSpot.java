
import java.util.Objects;

public class ParkingSpot {
    private final String spotId;
    private final int floorNumber;
    private final SpotType spotType;
    private Vehicle parkedVehicle;

    public ParkingSpot(String spotId, int floorNumber, SpotType spotType) {
        this.spotId = Objects.requireNonNull(spotId, "spotId cannot be null");
        this.floorNumber = floorNumber;
        this.spotType = Objects.requireNonNull(spotType, "spotType cannot be null");
    }

    public String getSpotId() {
        return spotId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public boolean isAvailable() {
        return parkedVehicle == null;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public boolean canFit(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }

        return switch (vehicle.getVehicleType()) {
            case BIKE -> spotType == SpotType.BIKE;
            case TRUCK -> spotType == SpotType.LARGE;
            case ELECTRIC_CAR -> spotType == SpotType.ELECTRIC;
            case CAR -> spotType == SpotType.COMPACT || spotType == SpotType.LARGE;
        };
    }

    public void park(Vehicle vehicle) {
        this.parkedVehicle = Objects.requireNonNull(vehicle, "vehicle cannot be null");
    }

    public void vacate() {
        this.parkedVehicle = null;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
            "spotId='" + spotId + '\'' +
            ", floorNumber=" + floorNumber +
            ", spotType=" + spotType +
            ", isAvailable=" + isAvailable() +
            '}';
    }
}
