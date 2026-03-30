package elevator.models;

import elevator.enums.DoorState;

/**
 * Represents the elevator door.
 * Encapsulates the door open/close behavior.
 */
public class ElevatorDoor {

    private DoorState state;

    public ElevatorDoor() {
        this.state = DoorState.CLOSED;
    }

    public void open() {
        if (state == DoorState.CLOSED) {
            state = DoorState.OPEN;
            System.out.println("  [Door] Door is now OPEN.");
        }
    }

    public void close() {
        if (state == DoorState.OPEN) {
            state = DoorState.CLOSED;
            System.out.println("  [Door] Door is now CLOSED.");
        }
    }

    public DoorState getState() {
        return state;
    }
}
