package elevator.controller;

import elevator.enums.Direction;
import elevator.models.Elevator;
import elevator.models.Request;
import elevator.strategy.ElevatorSelectionStrategy;
import elevator.strategy.NearestElevatorStrategy;

import java.util.List;

/**
 * ElevatorController — the central brain of the elevator system.
 *
 * Design Patterns:
 *  - Singleton: only one controller manages the whole building.
 *  - Strategy: delegates elevator selection to a pluggable strategy.
 *
 * Responsibilities:
 *  - Receive external requests (from floor panels).
 *  - Receive internal requests (from inside elevator cars).
 *  - Use the strategy to pick the best elevator.
 *  - Dispatch the request to the chosen elevator.
 */
public class ElevatorController {

    // ----------------------------------------------------------------
    // Singleton
    // ----------------------------------------------------------------

    private static ElevatorController instance;

    private ElevatorController() {
        // Default strategy can be swapped at any time
        this.strategy = new NearestElevatorStrategy();
    }

    public static ElevatorController getInstance() {
        if (instance == null) {
            instance = new ElevatorController();
        }
        return instance;
    }

    // ----------------------------------------------------------------
    // Fields
    // ----------------------------------------------------------------

    private List<Elevator> elevators;
    private ElevatorSelectionStrategy strategy;

    // ----------------------------------------------------------------
    // Configuration
    // ----------------------------------------------------------------

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    /** Allows swapping the selection strategy at runtime (Open/Closed Principle). */
    public void setStrategy(ElevatorSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    // ----------------------------------------------------------------
    // Request Handlers
    // ----------------------------------------------------------------

    /**
     * Handles an EXTERNAL request — someone pressed a button on a floor.
     *
     * @param sourceFloor      The floor where the button was pressed
     * @param destinationFloor Where the person wants to go
     * @param direction        The direction they want to travel
     */
    public void handleExternalRequest(int sourceFloor, int destinationFloor, Direction direction) {
        System.out.println("\n==============================");
        System.out.println("[Controller] External request received: Floor " + sourceFloor
                + " -> Floor " + destinationFloor + " (" + direction + ")");

        Request request = new Request(sourceFloor, destinationFloor, direction);
        Elevator selectedElevator = strategy.selectElevator(elevators, request);
        selectedElevator.addRequest(request);
        selectedElevator.processRequests();
    }

    /**
     * Handles an INTERNAL request — someone pressed a floor button inside the elevator.
     *
     * @param elevatorId       Which elevator car the button was pressed in
     * @param sourceFloor      The current floor of the elevator
     * @param destinationFloor The floor the passenger selected
     */
    public void handleInternalRequest(int elevatorId, int sourceFloor, int destinationFloor) {
        System.out.println("\n==============================");
        System.out.println("[Controller] Internal request: Elevator-" + elevatorId
                + " | Floor " + sourceFloor + " -> Floor " + destinationFloor);

        Direction direction = (destinationFloor > sourceFloor) ? Direction.UP : Direction.DOWN;
        Request request = new Request(sourceFloor, destinationFloor, direction);

        // Find the specific elevator by ID
        Elevator targetElevator = elevators.stream()
                .filter(e -> e.getId() == elevatorId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Elevator not found: " + elevatorId));

        targetElevator.addRequest(request);
        targetElevator.processRequests();
    }
}
