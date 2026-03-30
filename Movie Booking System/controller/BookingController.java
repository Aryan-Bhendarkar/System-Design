package moviebooking.controller;

import moviebooking.models.*;
import moviebooking.notification.BookingObserver;
import moviebooking.payment.PaymentStrategy;
import moviebooking.service.BookingService;
import moviebooking.service.MovieService;
import moviebooking.service.ShowService;

import java.util.List;
import java.util.Optional;

/**
 * Singleton Pattern + Facade Pattern
 *
 * BookingController is the single entry point for all client interactions.
 * It hides the complexity of individual services and exposes a clean API.
 *
 * Singleton ensures only ONE instance manages the system state.
 */
public class BookingController {

    // Singleton: only one instance allowed
    private static BookingController instance;

    private MovieService movieService;
    private ShowService showService;
    private BookingService bookingService;

    // Private constructor prevents external instantiation
    private BookingController() {
        this.movieService = new MovieService();
        this.showService = new ShowService();
        this.bookingService = new BookingService(showService);
    }

    // Thread-safe lazy initialization (double-checked locking)
    public static BookingController getInstance() {
        if (instance == null) {
            synchronized (BookingController.class) {
                if (instance == null) {
                    instance = new BookingController();
                }
            }
        }
        return instance;
    }

    // ---- Movie operations ----

    public void addMovie(Movie movie) {
        movieService.addMovie(movie);
    }

    public List<Movie> searchMovies(String title) {
        return movieService.searchByTitle(title);
    }

    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    // ---- Theatre & Screen setup (admin operations) ----

    public void addShow(Show show) {
        showService.addShow(show);
    }

    public List<Show> getShowsForMovie(String movieId) {
        return showService.getShowsByMovie(movieId);
    }

    public List<Seat> getAvailableSeats(String showId) {
        return showService.getAvailableSeats(showId);
    }

    // ---- Booking operations ----

    public void registerNotification(BookingObserver observer) {
        bookingService.addObserver(observer);
    }

    public Optional<Booking> bookTickets(User user, Show show, List<Seat> seats,
                                          PaymentStrategy paymentStrategy) {
        return bookingService.bookTickets(user, show, seats, paymentStrategy);
    }

    public Optional<Booking> getBooking(String bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    public List<Booking> getMyBookings(String userId) {
        return bookingService.getBookingsByUser(userId);
    }

    public boolean cancelBooking(String bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
}
