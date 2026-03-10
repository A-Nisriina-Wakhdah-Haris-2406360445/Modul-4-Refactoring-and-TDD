package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class VoucherPaymentStrategyTest {

  @Test
  void processPayment_success() {
    VoucherPaymentStrategy strategy = new VoucherPaymentStrategy();

    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "ESHOP1234ABC5678");

    String result = strategy.processPayment(paymentData);
    assertEquals("SUCCESS", result);
  }

  @Test
  void processPaymentRejectedNullVoucher() {
    VoucherPaymentStrategy strategy = new VoucherPaymentStrategy();
    Map<String, String> paymentData = new HashMap<>();

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }

  @Test
  void processPaymentWrongLength() {
    VoucherPaymentStrategy strategy = new VoucherPaymentStrategy();
     Map<String, String> paymentData =new HashMap<>();
     paymentData.put("voucherCode", "ESHOP123");

     String result = strategy.processPayment(paymentData);
     assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }

  @Test
  void processPaymentWrongPrefix() {
    VoucherPaymentStrategy strategy = new VoucherPaymentStrategy();
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "SHOP1234ABCD5678");

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }

  @Test
  void processPaymentWrongDigitCount() {
    VoucherPaymentStrategy strategy = new VoucherPaymentStrategy();

    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "ESHOPABCDEFGH123");

    String result = strategy.processPayment(paymentData);

    assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }
}
