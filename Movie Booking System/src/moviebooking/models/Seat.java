package moviebooking.models;

import moviebooking.enums.SeatType;
import moviebooking.enums.SeatStatus;

public class Seat {
    private String id;
    private String row;
    private int number;
    private SeatType seatType;
    private SeatStatus status;

    public Seat(String id, String row, int number, SeatType seatType) {
        this.id = id;
        this.row = row;
        this.number = number;
        this.seatType = seatType;
        this.status = SeatStatus.AVAILABLE;
    }

    public boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }

    public void lock() {
        this.status = SeatStatus.LOCKED;
    }

    public void book() {
        this.status = SeatStatus.BOOKED;
    }

    public void release() {
        this.status = SeatStatus.AVAILABLE;
    }

    public String getId() { return id; }
    public String getRow() { return row; }
    public int getNumber() { return number; }
    public SeatType getSeatType() { return seatType; }
    public SeatStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Seat{" + row + number + ", type=" + seatType + ", status=" + status + "}";
    }
}
