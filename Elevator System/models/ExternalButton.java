package elevator.models;

import elevator.enums.ButtonType;
import elevator.enums.Direction;

/**
 * Represents a button pressed on a floor panel (outside the elevator).
 * Has a direction (UP or DOWN) to signal intent.
 */
public class ExternalButton extends Button {

    private final Direction direction;

    public ExternalButton(Direction direction) {
        super(ButtonType.EXTERNAL);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void press() {
        super.press();
        System.out.println("[ExternalButton] Floor button pressed for direction: " + direction);
    }
}
