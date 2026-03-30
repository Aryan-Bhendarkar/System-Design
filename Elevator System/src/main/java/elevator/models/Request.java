package elevator.models;

import elevator.enums.Direction;

/**
 * Represents a request made to the elevator system.
 * Can be created from an external button press (with source & direction)
 * or an internal button press (with destination floor).
 */
public class Request {

    private final int sourceFloor;
    private final int destinationFloor;
    private final Direction direction;

    public Request(int sourceFloor, int destinationFloor, Direction direction) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Request{source=" + sourceFloor + ", destination=" + destinationFloor + ", direction=" + direction + "}";
    }
}
