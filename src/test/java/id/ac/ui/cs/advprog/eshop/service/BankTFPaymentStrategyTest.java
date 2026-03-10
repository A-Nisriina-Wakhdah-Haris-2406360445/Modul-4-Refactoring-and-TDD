package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BankTFPaymentStrategyTest {

  @Test
  void testProcessPaymentSuccess() {
    BankTransferPaymentStrategy strategy = new BankTransferPaymentStrategy();

    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("bankName", "BCA");
    paymentData.put("referenceCode", "TRX123456");

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.SUCCESS.getValue(), result);
  }

  @Test
  void testProcessPaymentBankNameNull() {
    BankTransferPaymentStrategy strategy = new BankTransferPaymentStrategy();

    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("referenceCode", "TRX123");

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }

  @Test
  void testProcessPaymentEmptyRefCode() {
    BankTransferPaymentStrategy strategy = new BankTransferPaymentStrategy();

    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("bankName", "BCA");
    paymentData.put("referenceCode", "");

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }
}
