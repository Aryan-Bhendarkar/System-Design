package moviebooking.notification;

import moviebooking.models.Booking;

/**
 * Observer Pattern – defines a contract for all notification types.
 */
public interface BookingObserver {
    void update(Booking booking);
}
