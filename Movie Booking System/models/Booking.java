package moviebooking.models;

import moviebooking.enums.BookingStatus;
import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    private String id;
    private User user;
    private Show show;
    private List<Seat> seats;
    private BookingStatus status;
    private Payment payment;
    private LocalDateTime bookingTime;

    public Booking(String id, User user, Show show, List<Seat> seats) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = BookingStatus.PENDING;
        this.bookingTime = LocalDateTime.now();
    }

    public void confirm(Payment payment) {
        this.payment = payment;
        this.status = BookingStatus.CONFIRMED;
        // Mark all seats as booked
        for (Seat seat : seats) {
            seat.book();
        }
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
        // Release all locked/booked seats
        for (Seat seat : seats) {
            seat.release();
        }
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public Show getShow() { return show; }
    public List<Seat> getSeats() { return seats; }
    public BookingStatus getStatus() { return status; }
    public Payment getPayment() { return payment; }
    public LocalDateTime getBookingTime() { return bookingTime; }

    @Override
    public String toString() {
        return "Booking{id='" + id + "', user='" + user.getName() + "', movie='" +
               show.getMovie().getTitle() + "', seats=" + seats.size() +
               ", status=" + status + "}";
    }
}
