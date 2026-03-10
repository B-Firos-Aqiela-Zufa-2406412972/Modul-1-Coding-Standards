package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;
    Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        validatePaymentData(paymentData);
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;
    }

    private void validatePaymentData(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}