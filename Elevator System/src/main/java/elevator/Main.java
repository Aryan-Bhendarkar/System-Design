package elevator;

import elevator.controller.ElevatorController;
import elevator.enums.Direction;
import elevator.models.Building;

/**
 * Main entry point — demonstrates the Elevator System.
 *
 * Scenario:
 *  Building: 10 floors, 3 elevators (all start at Floor 1)
 *
 *  Request 1 (External): Person on Floor 5 wants to go UP to Floor 9.
 *  Request 2 (External): Person on Floor 3 wants to go DOWN to Floor 1.
 *  Request 3 (Internal): Inside Elevator-2, passenger presses Floor 7.
 *  Request 4 (External): Person on Floor 8 wants to go UP to Floor 10.
 */
public class Main {

    public static void main(String[] args) {

        // Step 1: Create the building
        Building building = new Building("Tech Tower", 10, 3);

        // Step 2: Get the singleton controller and wire it up
        ElevatorController controller = ElevatorController.getInstance();
        controller.setElevators(building.getElevators());

        System.out.println("\n========== ELEVATOR SYSTEM DEMO ==========\n");

        // Step 3: Simulate requests

        // External: someone on floor 5 wants to go to floor 9 (going UP)
        controller.handleExternalRequest(5, 9, Direction.UP);

        // External: someone on floor 3 wants to go to floor 1 (going DOWN)
        controller.handleExternalRequest(3, 1, Direction.DOWN);

        // Internal: passenger already inside Elevator-2 presses floor 7
        controller.handleInternalRequest(2, 1, 7);

        // External: someone on floor 8 wants to go to floor 10 (going UP)
        controller.handleExternalRequest(8, 10, Direction.UP);

        System.out.println("========== DEMO COMPLETE ==========");
    }
}
