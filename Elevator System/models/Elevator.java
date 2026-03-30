package elevator.models;

import elevator.enums.Direction;
import elevator.enums.ElevatorState;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Core entity representing a single Elevator.
 *
 * Responsibilities:
 *  - Tracks current floor, direction, and state.
 *  - Maintains a queue of Requests to process.
 *  - Moves floor-by-floor and opens the door at each requested stop.
 *
 * Design: Uses State pattern implicitly via Direction + ElevatorState enums.
 */
public class Elevator {

    private final int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;
    private final ElevatorDoor door;
    private final Queue<Request> requestQueue;

    public Elevator(int id, int startFloor) {
        this.id = id;
        this.currentFloor = startFloor;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.door = new ElevatorDoor();
        this.requestQueue = new LinkedList<>();
    }

    // ----------------------------------------------------------------
    // Public API
    // ----------------------------------------------------------------

    public void addRequest(Request request) {
        requestQueue.add(request);
        System.out.println("[Elevator-" + id + "] New request added: " + request);
    }

    /**
     * Processes all pending requests one by one.
     * First moves to the source floor, picks up the passenger,
     * then moves to the destination floor.
     */
    public void processRequests() {
        while (!requestQueue.isEmpty()) {
            Request request = requestQueue.poll();

            // Step 1: Move to source floor to pick up the passenger
            System.out.println("\n[Elevator-" + id + "] Processing: " + request);
            moveToFloor(request.getSourceFloor());
            openAndCloseDoor();

            // Step 2: Move to destination floor to drop off the passenger
            moveToFloor(request.getDestinationFloor());
            openAndCloseDoor();
        }

        // All requests done — go IDLE
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        System.out.println("[Elevator-" + id + "] All requests processed. Now IDLE at floor " + currentFloor + ".\n");
    }

    /**
     * Simulates moving the elevator floor-by-floor to the target floor.
     */
    public void moveToFloor(int targetFloor) {
        if (targetFloor == currentFloor) {
            System.out.println("[Elevator-" + id + "] Already at floor " + currentFloor + ". No movement needed.");
            return;
        }

        this.state = ElevatorState.MOVING;
        this.direction = (targetFloor > currentFloor) ? Direction.UP : Direction.DOWN;

        System.out.println("[Elevator-" + id + "] Moving " + direction + " from floor " + currentFloor + " to floor " + targetFloor + ".");

        while (currentFloor != targetFloor) {
            if (direction == Direction.UP) {
                currentFloor++;
            } else {
                currentFloor--;
            }
            System.out.println("  -> Floor " + currentFloor);
        }

        System.out.println("[Elevator-" + id + "] Reached floor " + currentFloor + ".");
    }

    // ----------------------------------------------------------------
    // Helper
    // ----------------------------------------------------------------

    private void openAndCloseDoor() {
        door.open();
        door.close();
    }

    // ----------------------------------------------------------------
    // Getters
    // ----------------------------------------------------------------

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public boolean isIdle() {
        return state == ElevatorState.IDLE;
    }
}
