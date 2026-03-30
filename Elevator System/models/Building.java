package elevator.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entire Building.
 * Contains all floors and all elevators.
 */
public class Building {

    private final String name;
    private final List<Floor> floors;
    private final List<Elevator> elevators;

    public Building(String name, int numberOfFloors, int numberOfElevators) {
        this.name = name;
        this.floors = new ArrayList<>();
        this.elevators = new ArrayList<>();

        // Initialize floors (1-indexed for real-world feel)
        for (int i = 1; i <= numberOfFloors; i++) {
            floors.add(new Floor(i));
        }

        // Initialize elevators (all start at floor 1)
        for (int i = 1; i <= numberOfElevators; i++) {
            elevators.add(new Elevator(i, 1));
        }

        System.out.println("[Building] \"" + name + "\" created with "
                + numberOfFloors + " floors and " + numberOfElevators + " elevators.");
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public String getName() {
        return name;
    }
}
