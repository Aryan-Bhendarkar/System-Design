package moviebooking.payment;

/**
 * Strategy Pattern – defines a contract for all payment methods.
 * New payment types can be added without changing existing code (OCP).
 */
public interface PaymentStrategy {
    boolean pay(double amount);
    String getMethodName();
}
