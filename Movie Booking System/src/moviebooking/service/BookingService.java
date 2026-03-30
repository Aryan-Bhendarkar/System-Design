package moviebooking.service;

import moviebooking.models.*;
import moviebooking.enums.BookingStatus;
import moviebooking.enums.PaymentStatus;
import moviebooking.notification.BookingObserver;
import moviebooking.payment.PaymentStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Core booking service.
 * Orchestrates seat locking, payment and observer notifications.
 * Maintains a list of observers (Observer Pattern).
 */
public class BookingService {

    private Map<String, Booking> bookings;
    private ShowService showService;
    // Observer Pattern – list of interested listeners
    private List<BookingObserver> observers;

    public BookingService(ShowService showService) {
        this.bookings = new HashMap<>();
        this.showService = showService;
        this.observers = new ArrayList<>();
    }

    // ---- Observer management ----

    public void addObserver(BookingObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BookingObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.update(booking);
        }
    }

    // ---- Booking operations ----

    /**
     * Books seats for a user in a given show using the provided payment strategy.
     *
     * @return the confirmed Booking, or empty if booking failed
     */
    public Optional<Booking> bookTickets(User user, Show show, List<Seat> selectedSeats,
                                          PaymentStrategy paymentStrategy) {

        // Step 1: Lock seats atomically
        System.out.println("\n[BookingService] Attempting to lock " + selectedSeats.size() + " seat(s)...");
        boolean locked = showService.lockSeats(selectedSeats);
        if (!locked) {
            System.out.println("[BookingService] Could not lock seats. Booking aborted.");
            return Optional.empty();
        }
        System.out.println("[BookingService] Seats locked successfully.");

        // Step 2: Create a pending booking
        String bookingId = "BKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Booking booking = new Booking(bookingId, user, show, selectedSeats);

        // Step 3: Calculate total amount
        double totalAmount = show.calculateTotalPrice(selectedSeats);
        System.out.println("[BookingService] Total amount to pay: ₹" + totalAmount);

        // Step 4: Process payment using the injected strategy (Strategy Pattern)
        boolean paymentSuccess = paymentStrategy.pay(totalAmount);

        if (!paymentSuccess) {
            // Payment failed – release the locked seats
            System.out.println("[BookingService] Payment failed! Releasing locked seats.");
            booking.cancel();
            return Optional.empty();
        }

        // Step 5: Create payment record and confirm booking
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Payment payment = new Payment(paymentId, totalAmount, paymentStrategy.getMethodName());
        payment.markSuccess();

        booking.confirm(payment);
        bookings.put(bookingId, booking);

        System.out.println("[BookingService] Booking confirmed! ID: " + bookingId);

        // Step 6: Notify all observers (Observer Pattern)
        notifyObservers(booking);

        return Optional.of(booking);
    }

    public Optional<Booking> getBookingById(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public List<Booking> getBookingsByUser(String userId) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getUser().getId().equals(userId)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    public boolean cancelBooking(String bookingId) {
        Optional<Booking> bookingOpt = getBookingById(bookingId);
        if (!bookingOpt.isPresent()) {
            System.out.println("[BookingService] Booking not found: " + bookingId);
            return false;
        }
        Booking booking = bookingOpt.get();
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("[BookingService] Booking already cancelled.");
            return false;
        }
        booking.cancel();
        System.out.println("[BookingService] Booking " + bookingId + " cancelled. Seats released.");
        return true;
    }
}
