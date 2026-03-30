package moviebooking.payment;

public class DebitCardPayment implements PaymentStrategy {

    private String cardNumber;

    public DebitCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("[Debit Card] Processing payment of ₹" + amount +
                           " using card ending in " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("[Debit Card] Payment successful!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "DEBIT_CARD";
    }
}
