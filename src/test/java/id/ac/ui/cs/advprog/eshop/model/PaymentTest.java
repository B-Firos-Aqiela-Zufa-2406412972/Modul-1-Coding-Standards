package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



class PaymentTest {
    Map<String, String> paymentData;
    Order order;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId("prod-1");
        product.setName("Kecap");
        product.setQuantity(1);
        products.add(product);

        order = new Order("order-1", products, 12345678L, "Author");
    }

    @Test
    void testCreatePayment() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", paymentData, order);

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertSame(paymentData, payment.getPaymentData());
        assertSame(order, payment.getOrder());
    }

    @Test
    void testCreatePaymentWithEmptyPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", new HashMap<>(), order);
        });
    }
}