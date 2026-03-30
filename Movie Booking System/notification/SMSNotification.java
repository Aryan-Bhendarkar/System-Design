package moviebooking.notification;

import moviebooking.models.Booking;

public class SMSNotification implements BookingObserver {

    @Override
    public void update(Booking booking) {
        System.out.println("[SMS] Sending SMS to: " + booking.getUser().getPhone());
        System.out.println("[SMS] Booking Confirmed! Movie: " +
                           booking.getShow().getMovie().getTitle() + " | Seats: " +
                           booking.getSeats().size() + " | Amount Paid: ₹" +
                           booking.getPayment().getAmount());
    }
}
