package moviebooking.notification;

import moviebooking.models.Booking;

public class EmailNotification implements BookingObserver {

    @Override
    public void update(Booking booking) {
        System.out.println("[Email] Sending booking confirmation to: " +
                           booking.getUser().getEmail());
        System.out.println("[Email] Dear " + booking.getUser().getName() +
                           ", your booking (ID: " + booking.getId() + ") for '" +
                           booking.getShow().getMovie().getTitle() + "' is CONFIRMED!");
    }
}
