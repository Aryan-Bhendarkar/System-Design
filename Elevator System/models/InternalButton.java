package elevator.models;

import elevator.enums.ButtonType;

/**
 * Represents a button pressed inside the elevator car.
 * Used to select the destination floor.
 */
public class InternalButton extends Button {

    private final int destinationFloor;

    public InternalButton(int destinationFloor) {
        super(ButtonType.INTERNAL);
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    @Override
    public void press() {
        super.press();
        System.out.println("[InternalButton] Destination floor selected: " + destinationFloor);
    }
}
