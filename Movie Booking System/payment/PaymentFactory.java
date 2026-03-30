package moviebooking.payment;

/**
 * Factory Pattern – creates the correct PaymentStrategy based on a method name.
 * Decouples the consumer from knowing which class to instantiate.
 */
public class PaymentFactory {

    public static PaymentStrategy getPaymentStrategy(String method, String identifier) {
        switch (method.toUpperCase()) {
            case "CREDIT_CARD":
                return new CreditCardPayment(identifier);
            case "DEBIT_CARD":
                return new DebitCardPayment(identifier);
            case "UPI":
                return new UPIPayment(identifier);
            default:
                throw new IllegalArgumentException("Unknown payment method: " + method);
        }
    }
}
