package elevator.models;

import elevator.enums.ButtonType;

/**
 * Abstract base class for all types of buttons.
 * Follows the Open/Closed Principle - open for extension (new button types),
 * closed for modification.
 */
public abstract class Button {

    protected boolean isPressed;
    protected ButtonType buttonType;

    public Button(ButtonType buttonType) {
        this.buttonType = buttonType;
        this.isPressed = false;
    }

    public void press() {
        this.isPressed = true;
        System.out.println("[Button] " + buttonType + " button pressed.");
    }

    public void reset() {
        this.isPressed = false;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }
}
