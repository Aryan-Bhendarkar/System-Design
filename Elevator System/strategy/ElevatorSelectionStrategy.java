package elevator.strategy;

import elevator.models.Elevator;
import elevator.models.Request;

import java.util.List;

/**
 * Strategy interface for selecting which elevator should serve a request.
 *
 * Using Strategy Pattern allows us to swap the selection algorithm
 * without touching the ElevatorController.
 */
public interface ElevatorSelectionStrategy {

    /**
     * Given a list of available elevators and the incoming request,
     * select and return the best elevator to handle it.
     *
     * @param elevators All elevators in the building
     * @param request   The incoming request to fulfill
     * @return The selected elevator
     */
    Elevator selectElevator(List<Elevator> elevators, Request request);
}
