package moviebooking.models;

import moviebooking.enums.SeatType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Show {
    private String id;
    private Movie movie;
    private Screen screen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // Price per seat type
    private Map<SeatType, Double> seatPriceMap;

    public Show(String id, Movie movie, Screen screen, LocalDateTime startTime,
                Map<SeatType, Double> seatPriceMap) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(movie.getDurationMins());
        this.seatPriceMap = seatPriceMap;
    }

    public double getPriceForSeat(SeatType seatType) {
        return seatPriceMap.getOrDefault(seatType, 0.0);
    }

    public double calculateTotalPrice(List<Seat> seats) {
        double total = 0;
        for (Seat seat : seats) {
            total += getPriceForSeat(seat.getSeatType());
        }
        return total;
    }

    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Map<SeatType, Double> getSeatPriceMap() { return seatPriceMap; }

    @Override
    public String toString() {
        return "Show{id='" + id + "', movie='" + movie.getTitle() + "', screen='" +
               screen.getName() + "', startTime=" + startTime + "}";
    }
}
