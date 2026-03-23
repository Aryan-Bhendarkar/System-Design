public class ParkingSpot {
    private final String spotId;
    private final SpotType type;
    private Vehicle parkedVehicle;

    public ParkingSpot(String spotId, SpotType type) {
        this.spotId = spotId;
        this.type = type;
    }

    public boolean isFree() {
        return parkedVehicle == null;
    }

    public boolean canFit(VehicleType vehicleType) {
        if (type == SpotType.TRUCK) {
            return true;
        }
        if (type == SpotType.CAR) {
            return vehicleType == VehicleType.CAR || vehicleType == VehicleType.BIKE;
        }
        return vehicleType == VehicleType.BIKE;
    }

    public void park(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
    }

    public void vacate() {
        this.parkedVehicle = null;
    }

    public String getSpotId() {
        return spotId;
    }

    public SpotType getType() {
        return type;
    }
}
