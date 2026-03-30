package moviebooking.payment;

public class UPIPayment implements PaymentStrategy {

    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("[UPI] Processing payment of ₹" + amount +
                           " via UPI ID: " + upiId);
        System.out.println("[UPI] Payment successful!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "UPI";
    }
}
