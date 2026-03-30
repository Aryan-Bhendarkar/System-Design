package moviebooking.models;

import moviebooking.enums.PaymentStatus;

public class Payment {
    private String id;
    private double amount;
    private String paymentMethod;
    private PaymentStatus status;

    public Payment(String id, double amount, String paymentMethod) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PENDING;
    }

    public void markSuccess() {
        this.status = PaymentStatus.SUCCESS;
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
    }

    public String getId() { return id; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public PaymentStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Payment{id='" + id + "', amount=₹" + amount + ", method='" +
               paymentMethod + "', status=" + status + "}";
    }
}
