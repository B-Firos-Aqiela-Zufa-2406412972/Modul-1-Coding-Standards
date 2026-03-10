package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData, order);

        if ("VOUCHER".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            if (isValidVoucher(voucherCode)) {
                payment.setStatus("SUCCESS");
            } else {
                payment.setStatus("REJECTED");
            }
        } else if ("BANK_TRANSFER".equals(method)) {
            if (isValidBankTransfer(paymentData)) {
                payment.setStatus("SUCCESS");
            } else {
                payment.setStatus("REJECTED");
            }
        }

        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        if ("SUCCESS".equals(status)) {
            payment.getOrder().setStatus("SUCCESS");
        } else if ("REJECTED".equals(status)) {
            payment.getOrder().setStatus("FAILED");
        }
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private boolean isValidVoucher(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }
        int numCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) numCount++;
        }
        return numCount == 8;
    }

    private boolean isValidBankTransfer(Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        return bankName != null && !bankName.trim().isEmpty() &&
                referenceCode != null && !referenceCode.trim().isEmpty();
    }
}