package elevator.strategy;

import elevator.models.Elevator;
import elevator.models.Request;

import java.util.List;

/**
 * Concrete Strategy: picks the elevator closest to the source floor.
 *
 * Algorithm:
 *  1. Prefer IDLE elevators over MOVING ones.
 *  2. Among all elevators, pick the one with the least distance to sourceFloor.
 *
 * This is a simple greedy approach suitable for LLD demonstrations.
 */
public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, Request request) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());

            // Prefer idle elevators — treat them as having a bonus of 0 extra cost
            boolean isBetter = (bestElevator == null)
                    || (elevator.isIdle() && !bestElevator.isIdle())
                    || (elevator.isIdle() == bestElevator.isIdle() && distance < minDistance);

            if (isBetter) {
                bestElevator = elevator;
                minDistance = distance;
            }
        }

        System.out.println("[NearestElevatorStrategy] Selected Elevator-" + bestElevator.getId()
                + " (currently at floor " + bestElevator.getCurrentFloor() + ") for " + request);
        return bestElevator;
    }
}
