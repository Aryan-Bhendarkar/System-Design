package elevator.models;

/**
 * Represents a single floor in the building.
 * Each floor has two external buttons: one for UP, one for DOWN.
 * The ground floor typically has only UP and the top floor only DOWN,
 * but for simplicity we keep both on every floor.
 */
public class Floor {

    private final int floorNumber;
    private final ExternalButton upButton;
    private final ExternalButton downButton;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.upButton = new ExternalButton(elevator.enums.Direction.UP);
        this.downButton = new ExternalButton(elevator.enums.Direction.DOWN);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ExternalButton getUpButton() {
        return upButton;
    }

    public ExternalButton getDownButton() {
        return downButton;
    }
}
