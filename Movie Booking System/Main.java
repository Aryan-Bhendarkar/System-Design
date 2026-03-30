package moviebooking;

import moviebooking.controller.BookingController;
import moviebooking.enums.Genre;
import moviebooking.enums.SeatType;
import moviebooking.models.*;
import moviebooking.notification.EmailNotification;
import moviebooking.notification.SMSNotification;
import moviebooking.payment.PaymentFactory;
import moviebooking.payment.PaymentStrategy;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Entry point – demonstrates the complete movie ticket booking flow.
 *
 * Flow:
 *  1. Admin sets up theatre, screen, movie, show
 *  2. User searches for a movie
 *  3. User views available seats
 *  4. User books seats with a payment method
 *  5. System confirms booking and sends notifications
 *  6. Demonstrate double-booking prevention
 */
public class Main {

    public static void main(String[] args) {

        // =====================================================
        // 1. Get Singleton instance of BookingController
        // =====================================================
        BookingController controller = BookingController.getInstance();

        // =====================================================
        // 2. Register notification observers (Observer Pattern)
        // =====================================================
        controller.registerNotification(new EmailNotification());
        controller.registerNotification(new SMSNotification());

        System.out.println("======================================================");
        System.out.println("        MOVIE TICKET BOOKING SYSTEM - LLD DEMO        ");
        System.out.println("======================================================\n");

        // =====================================================
        // 3. Admin: Setup Theatre, Screens, Seats
        // =====================================================
        System.out.println("--- [ADMIN] Setting up Theatre ---");
        Theatre theatre = new Theatre("T1", "PVR Cinema", "Mumbai",
                                       "Oberoi Mall, Goregaon East");

        Screen screen1 = new Screen("S1", "Audi 1", "T1");

        // Add 10 REGULAR seats (Row A)
        for (int i = 1; i <= 10; i++) {
            screen1.addSeat(new Seat("S1-A" + i, "A", i, SeatType.REGULAR));
        }
        // Add 5 PREMIUM seats (Row B)
        for (int i = 1; i <= 5; i++) {
            screen1.addSeat(new Seat("S1-B" + i, "B", i, SeatType.PREMIUM));
        }

        theatre.addScreen(screen1);
        System.out.println("Theatre setup complete: " + theatre);
        System.out.println("Screen: " + screen1);
        System.out.println("Total seats: " + screen1.getSeats().size());

        // =====================================================
        // 4. Admin: Add Movies
        // =====================================================
        System.out.println("\n--- [ADMIN] Adding Movies ---");
        Movie movie1 = new Movie("M1", "Avengers: Endgame", Genre.ACTION, 182, "English");
        Movie movie2 = new Movie("M2", "Inception", Genre.SCI_FI, 148, "English");
        controller.addMovie(movie1);
        controller.addMovie(movie2);

        // =====================================================
        // 5. Admin: Add Shows
        // =====================================================
        System.out.println("\n--- [ADMIN] Adding Shows ---");
        Map<SeatType, Double> priceMap = new HashMap<>();
        priceMap.put(SeatType.REGULAR, 250.0);
        priceMap.put(SeatType.PREMIUM, 450.0);

        Show show1 = new Show("SH1", movie1, screen1,
                              LocalDateTime.of(2026, 3, 30, 18, 0), priceMap);
        controller.addShow(show1);

        // =====================================================
        // 6. User: Search Movies
        // =====================================================
        System.out.println("\n--- [USER] Searching for 'Avengers' ---");
        List<Movie> results = controller.searchMovies("Avengers");
        results.forEach(System.out::println);

        // =====================================================
        // 7. User: View available shows and seats
        // =====================================================
        System.out.println("\n--- [USER] Checking available shows for 'Avengers: Endgame' ---");
        List<Show> shows = controller.getShowsForMovie("M1");
        shows.forEach(System.out::println);

        System.out.println("\n--- [USER] Checking available seats for Show: SH1 ---");
        List<Seat> availableSeats = controller.getAvailableSeats("SH1");
        availableSeats.forEach(System.out::println);

        // =====================================================
        // 8. User: Book tickets (with UPI payment – Strategy+Factory)
        // =====================================================
        System.out.println("\n--- [USER] Booking 2 REGULAR + 1 PREMIUM seat ---");

        User user1 = new User("U1", "Aryan Bhendarkar", "aryan@example.com", "9876543210");

        // Pick seats A1, A2, B1
        Seat seatA1 = availableSeats.stream()
                .filter(s -> s.getId().equals("S1-A1")).findFirst().get();
        Seat seatA2 = availableSeats.stream()
                .filter(s -> s.getId().equals("S1-A2")).findFirst().get();
        Seat seatB1 = availableSeats.stream()
                .filter(s -> s.getId().equals("S1-B1")).findFirst().get();

        List<Seat> selectedSeats = Arrays.asList(seatA1, seatA2, seatB1);

        // Use Factory to create payment strategy (Factory Pattern)
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentStrategy("UPI", "aryan@upi");

        Optional<Booking> bookingOpt = controller.bookTickets(user1, show1, selectedSeats, paymentStrategy);

        bookingOpt.ifPresent(booking -> {
            System.out.println("\n✅ Booking Summary:");
            System.out.println(booking);
            System.out.println("Payment: " + booking.getPayment());
        });

        // =====================================================
        // 9. Double-booking prevention demo
        // =====================================================
        System.out.println("\n--- [USER 2] Trying to book the SAME seats (should fail) ---");

        User user2 = new User("U2", "Rahul Sharma", "rahul@example.com", "9123456780");
        PaymentStrategy user2Payment = PaymentFactory.getPaymentStrategy("CREDIT_CARD", "4111111111111111");

        Optional<Booking> failedBooking = controller.bookTickets(user2, show1, selectedSeats, user2Payment);

        if (!failedBooking.isPresent()) {
            System.out.println("❌ Booking failed as expected – seats are already booked!");
        }

        // =====================================================
        // 10. User 2 books remaining seats
        // =====================================================
        System.out.println("\n--- [USER 2] Booking different available seats ---");
        List<Seat> remainingSeats = controller.getAvailableSeats("SH1");
        System.out.println("Remaining available seats: " + remainingSeats.size());

        if (remainingSeats.size() >= 2) {
            List<Seat> user2Seats = Arrays.asList(remainingSeats.get(0), remainingSeats.get(1));
            Optional<Booking> user2Booking = controller.bookTickets(user2, show1, user2Seats, user2Payment);
            user2Booking.ifPresent(b -> {
                System.out.println("\n✅ User 2 Booking Summary:");
                System.out.println(b);
            });
        }

        System.out.println("\n======================================================");
        System.out.println("                   DEMO COMPLETE                    ");
        System.out.println("======================================================");
    }
}
