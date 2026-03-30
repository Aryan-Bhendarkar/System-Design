package moviebooking.models;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private String id;
    private String name;
    private String theatreId;
    private List<Seat> seats;

    public Screen(String id, String name, String theatreId) {
        this.id = id;
        this.name = name;
        this.theatreId = theatreId;
        this.seats = new ArrayList<>();
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getTheatreId() { return theatreId; }
    public List<Seat> getSeats() { return seats; }

    @Override
    public String toString() {
        return "Screen{id='" + id + "', name='" + name + "', totalSeats=" + seats.size() + "}";
    }
}
