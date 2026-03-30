package moviebooking.service;

import moviebooking.models.Show;
import moviebooking.models.Seat;
import moviebooking.enums.SeatStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages shows and seat availability.
 * Single Responsibility: Only handles show-related operations.
 */
public class ShowService {

    private List<Show> shows;

    public ShowService() {
        this.shows = new ArrayList<>();
    }

    public void addShow(Show show) {
        shows.add(show);
        System.out.println("[ShowService] Added show for movie: " +
                           show.getMovie().getTitle() + " at " + show.getStartTime());
    }

    public List<Show> getAllShows() {
        return shows;
    }

    public Optional<Show> getShowById(String showId) {
        return shows.stream()
                    .filter(s -> s.getId().equals(showId))
                    .findFirst();
    }

    public List<Show> getShowsByMovie(String movieId) {
        List<Show> result = new ArrayList<>();
        for (Show show : shows) {
            if (show.getMovie().getId().equals(movieId)) {
                result.add(show);
            }
        }
        return result;
    }

    public List<Seat> getAvailableSeats(String showId) {
        Optional<Show> showOpt = getShowById(showId);
        if (!showOpt.isPresent()) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }
        List<Seat> available = new ArrayList<>();
        for (Seat seat : showOpt.get().getScreen().getSeats()) {
            if (seat.isAvailable()) {
                available.add(seat);
            }
        }
        return available;
    }

    /**
     * Lock seats temporarily to prevent double-booking during payment.
     */
    public boolean lockSeats(List<Seat> seats) {
        // Validate all are still available before locking any
        for (Seat seat : seats) {
            if (!seat.isAvailable()) {
                System.out.println("[ShowService] Seat " + seat + " is no longer available!");
                return false;
            }
        }
        for (Seat seat : seats) {
            seat.lock();
        }
        return true;
    }
}
