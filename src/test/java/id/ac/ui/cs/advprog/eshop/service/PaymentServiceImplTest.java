package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId("prod-1");
        product.setName("Kecap");
        product.setQuantity(1);
        products.add(product);

        order = new Order("order-1", products, 12345678L, "Author");
    }

    @Test
    void testAddPaymentVoucherValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123");

        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testAddPaymentBankTransferValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF12345");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAddPaymentBankTransferInvalidEmptyData() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF12345");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToSuccessUpdatesOrderStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-1", "VOUCHER", paymentData, order);

        paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testSetStatusToRejectedUpdatesOrderStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-1", "VOUCHER", paymentData, order);

        paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }
}