# 🏢 Elevator System – Low Level Design (LLD)

A clean, pattern-driven implementation of an **Elevator System** in Java.

---

## 📐 Class Diagram

```mermaid
classDiagram
    direction TB

    class Direction {
        <<enumeration>>
        UP
        DOWN
        IDLE
    }

    class ElevatorState {
        <<enumeration>>
        MOVING
        IDLE
        MAINTENANCE
    }

    class DoorState {
        <<enumeration>>
        OPEN
        CLOSED
    }

    class ButtonType {
        <<enumeration>>
        INTERNAL
        EXTERNAL
    }

    class Button {
        <<abstract>>
        #boolean isPressed
        #ButtonType buttonType
        +press()
        +reset()
        +isPressed() boolean
    }

    class InternalButton {
        -int destinationFloor
        +getDestinationFloor() int
        +press()
    }

    class ExternalButton {
        -Direction direction
        +getDirection() Direction
        +press()
    }

    Button <|-- InternalButton
    Button <|-- ExternalButton

    class Request {
        -int sourceFloor
        -int destinationFloor
        -Direction direction
        +getSourceFloor() int
        +getDestinationFloor() int
        +getDirection() Direction
    }

    class Floor {
        -int floorNumber
        -ExternalButton upButton
        -ExternalButton downButton
        +getFloorNumber() int
        +getUpButton() ExternalButton
        +getDownButton() ExternalButton
    }

    class ElevatorDoor {
        -DoorState state
        +open()
        +close()
        +getState() DoorState
    }

    class Elevator {
        -int id
        -int currentFloor
        -Direction direction
        -ElevatorState state
        -Queue~Request~ requestQueue
        -ElevatorDoor door
        +addRequest(Request)
        +processRequests()
        +moveToFloor(int)
        +isIdle() boolean
    }

    class Building {
        -String name
        -List~Floor~ floors
        -List~Elevator~ elevators
        +getFloors() List~Floor~
        +getElevators() List~Elevator~
    }

    class ElevatorSelectionStrategy {
        <<interface>>
        +selectElevator(List~Elevator~, Request) Elevator
    }

    class NearestElevatorStrategy {
        +selectElevator(List~Elevator~, Request) Elevator
    }

    class ElevatorController {
        -static ElevatorController instance
        -ElevatorSelectionStrategy strategy
        -List~Elevator~ elevators
        +getInstance() ElevatorController
        +setStrategy(ElevatorSelectionStrategy)
        +handleExternalRequest(int, int, Direction)
        +handleInternalRequest(int, int, int)
    }

    ElevatorSelectionStrategy <|.. NearestElevatorStrategy
    ElevatorController --> ElevatorSelectionStrategy : uses
    ElevatorController --> Elevator : manages
    Building --> Floor : contains
    Building --> Elevator : contains
    Floor --> ExternalButton : has
    Elevator --> ElevatorDoor : has
    Elevator --> Request : processes
    Request --> Direction : has
```

---

## 🏗️ Design Patterns Used

| Pattern | Where Applied | Why |
|---|---|---|
| **Singleton** | `ElevatorController` | Only one controller should manage the whole building |
| **Strategy** | `ElevatorSelectionStrategy` | Swap elevator-picking algorithm without changing the controller |
| **State** | `ElevatorState` + `Direction` enums | Model the lifecycle of an elevator cleanly |
| **Template Method** | `Button` → `InternalButton` / `ExternalButton` | Common press logic in base class, specifics in subclass |

---

## 📁 Project Structure

```
Elevator System/
└── src/
    └── main/
        └── java/
            └── elevator/
                ├── Main.java
                ├── enums/
                │   ├── Direction.java
                │   ├── ElevatorState.java
                │   ├── DoorState.java
                │   └── ButtonType.java
                ├── models/
                │   ├── Button.java
                │   ├── InternalButton.java
                │   ├── ExternalButton.java
                │   ├── Request.java
                │   ├── Floor.java
                │   ├── ElevatorDoor.java
                │   ├── Elevator.java
                │   └── Building.java
                ├── strategy/
                │   ├── ElevatorSelectionStrategy.java
                │   └── NearestElevatorStrategy.java
                └── controller/
                    └── ElevatorController.java
```

---

## ▶️ How to Run

### Using Maven
```bash
cd "Elevator System"
mvn compile
mvn exec:java -Dexec.mainClass="elevator.Main"
```

### Using javac manually
```bash
cd "Elevator System/src/main/java"
javac elevator/enums/*.java elevator/models/*.java elevator/strategy/*.java elevator/controller/*.java elevator/Main.java
java elevator.Main
```

---

## 🎮 Demo Scenario

The `Main.java` runs the following scenario in a **10-floor, 3-elevator** building:

| # | Type | From | To | Direction |
|---|---|---|---|---|
| 1 | External | Floor 5 | Floor 9 | UP |
| 2 | External | Floor 3 | Floor 1 | DOWN |
| 3 | Internal | Floor 1 | Floor 7 | UP (Elevator-2) |
| 4 | External | Floor 8 | Floor 10 | UP |

---

## 🔑 Key Design Decisions

1. **Single Responsibility**: Each class has one clear job — `Elevator` moves, `ElevatorController` dispatches, `NearestElevatorStrategy` selects.
2. **Open/Closed**: Adding a `PriorityElevatorStrategy` or `ZoneBasedStrategy` requires zero changes to existing classes.
3. **Dependency Inversion**: `ElevatorController` depends on the `ElevatorSelectionStrategy` interface, not a concrete class.
