# 🎬 Movie Ticket Booking System – LLD

A **Low-Level Design (LLD)** implementation of a movie ticket booking system in Java, demonstrating SOLID principles and common design patterns.

---

## 📐 Class Diagram

```mermaid
classDiagram
    direction TB

    class Movie {
        -String id
        -String title
        -Genre genre
        -int durationMins
        -String language
    }

    class Theatre {
        -String id
        -String name
        -String city
        -List~Screen~ screens
        +addScreen(Screen)
    }

    class Screen {
        -String id
        -String name
        -String theatreId
        -List~Seat~ seats
        +addSeat(Seat)
    }

    class Seat {
        -String id
        -String row
        -int number
        -SeatType seatType
        -SeatStatus status
        +lock()
        +book()
        +release()
        +isAvailable() bool
    }

    class Show {
        -String id
        -Movie movie
        -Screen screen
        -LocalDateTime startTime
        -Map~SeatType, Double~ seatPriceMap
        +getPriceForSeat(SeatType) double
        +calculateTotalPrice(List~Seat~) double
    }

    class User {
        -String id
        -String name
        -String email
        -String phone
    }

    class Booking {
        -String id
        -User user
        -Show show
        -List~Seat~ seats
        -BookingStatus status
        -Payment payment
        +confirm(Payment)
        +cancel()
    }

    class Payment {
        -String id
        -double amount
        -String paymentMethod
        -PaymentStatus status
        +markSuccess()
        +markFailed()
    }

    %% Strategy Pattern
    class PaymentStrategy {
        <<interface>>
        +pay(double amount) bool
        +getMethodName() String
    }
    class CreditCardPayment {
        +pay(double amount) bool
    }
    class DebitCardPayment {
        +pay(double amount) bool
    }
    class UPIPayment {
        +pay(double amount) bool
    }

    %% Factory Pattern
    class PaymentFactory {
        +getPaymentStrategy(String, String) PaymentStrategy$
    }

    %% Observer Pattern
    class BookingObserver {
        <<interface>>
        +update(Booking)
    }
    class EmailNotification {
        +update(Booking)
    }
    class SMSNotification {
        +update(Booking)
    }

    %% Services
    class MovieService {
        -List~Movie~ movies
        +addMovie(Movie)
        +searchByTitle(String) List~Movie~
    }
    class ShowService {
        -List~Show~ shows
        +addShow(Show)
        +getAvailableSeats(String) List~Seat~
        +lockSeats(List~Seat~) bool
    }
    class BookingService {
        -List~BookingObserver~ observers
        +addObserver(BookingObserver)
        +bookTickets(User, Show, List~Seat~, PaymentStrategy) Optional~Booking~
        +cancelBooking(String) bool
    }

    %% Singleton + Facade
    class BookingController {
        -static BookingController instance
        -MovieService movieService
        -ShowService showService
        -BookingService bookingService
        +getInstance() BookingController$
        +addMovie(Movie)
        +bookTickets(User, Show, List~Seat~, PaymentStrategy) Optional~Booking~
    }

    %% Enums
    class SeatType {
        <<enumeration>>
        REGULAR
        PREMIUM
    }
    class SeatStatus {
        <<enumeration>>
        AVAILABLE
        LOCKED
        BOOKED
    }
    class BookingStatus {
        <<enumeration>>
        PENDING
        CONFIRMED
        CANCELLED
    }

    Theatre "1" *-- "many" Screen
    Screen "1" *-- "many" Seat
    Show --> Movie
    Show --> Screen
    Booking --> User
    Booking --> Show
    Booking "1" *-- "many" Seat
    Booking --> Payment

    PaymentStrategy <|.. CreditCardPayment
    PaymentStrategy <|.. DebitCardPayment
    PaymentStrategy <|.. UPIPayment
    PaymentFactory ..> PaymentStrategy

    BookingObserver <|.. EmailNotification
    BookingObserver <|.. SMSNotification

    BookingController --> MovieService
    BookingController --> ShowService
    BookingController --> BookingService
    BookingService --> ShowService
    BookingService --> BookingObserver
    BookingService ..> PaymentStrategy

    Seat --> SeatType
    Seat --> SeatStatus
    Booking --> BookingStatus
```

---

## 🎯 Design Patterns Used

| Pattern | Class(es) | Description |
|---|---|---|
| **Singleton** | `BookingController` | Only one instance manages the entire system |
| **Strategy** | `PaymentStrategy`, `CreditCardPayment`, `DebitCardPayment`, `UPIPayment` | Interchangeable payment methods without changing BookingService |
| **Observer** | `BookingObserver`, `EmailNotification`, `SMSNotification` | Notify interested parties when a booking is confirmed |
| **Factory** | `PaymentFactory` | Decouples payment creation from the caller |
| **Facade** | `BookingController` | Simplifies access to the subsystem (MovieService, ShowService, BookingService) |

---

## 📐 SOLID Principles

| Principle | How Applied |
|---|---|
| **SRP** | Each class has a single job (`Seat` handles seat state, `BookingService` handles booking logic) |
| **OCP** | New payment methods extend `PaymentStrategy` without touching existing code |
| **LSP** | All `PaymentStrategy` implementations are fully substitutable |
| **ISP** | `BookingObserver` and `PaymentStrategy` are separate focused interfaces |
| **DIP** | `BookingService` depends on `PaymentStrategy` interface, not concrete classes |

---

## 📁 Project Structure

```
src/
└── moviebooking/
    ├── enums/          → SeatType, SeatStatus, BookingStatus, Genre, PaymentStatus
    ├── models/         → Movie, Theatre, Screen, Seat, Show, User, Booking, Payment
    ├── payment/        → PaymentStrategy (interface), CreditCardPayment, DebitCardPayment, UPIPayment, PaymentFactory
    ├── notification/   → BookingObserver (interface), EmailNotification, SMSNotification
    ├── service/        → MovieService, ShowService, BookingService
    ├── controller/     → BookingController (Singleton + Facade)
    └── Main.java       → Demo runner
```

---

## 🚀 How to Run

```bash
# From the project root (Movie Booking System/)
javac -d out src/moviebooking/enums/*.java src/moviebooking/models/*.java src/moviebooking/payment/*.java src/moviebooking/notification/*.java src/moviebooking/service/*.java src/moviebooking/controller/*.java src/moviebooking/Main.java

java -cp out moviebooking.Main
```

---

## 🔑 Key Features

- 🎟️ Browse movies and shows
- 💺 Real-time seat availability check
- 🔒 Atomic seat locking to prevent double-booking
- 💳 Multiple payment methods (UPI / Credit Card / Debit Card)
- 📧 Email & SMS notifications on booking confirmation
- ❌ Booking cancellation with seat release
