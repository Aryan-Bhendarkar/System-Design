package moviebooking.payment;

public class CreditCardPayment implements PaymentStrategy {

    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        // Simulate payment gateway call
        System.out.println("[Credit Card] Processing payment of ₹" + amount +
                           " using card ending in " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("[Credit Card] Payment successful!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "CREDIT_CARD";
    }
}
