# 🎬 Movie Ticket Booking System – LLD

A **Low-Level Design (LLD)** implementation of a movie ticket booking system in Java.

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
