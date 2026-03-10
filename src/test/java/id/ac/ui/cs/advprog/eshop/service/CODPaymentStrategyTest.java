package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CODPaymentStrategyTest {

  @Test
  void processPaymentSuccess() {
    CODPaymentStrategy strategy = new CODPaymentStrategy();
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("address", "Depok");
    paymentData.put("deliveryFee", "10000");

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.SUCCESS.getValue(), result);
  }

  @Test
  void testProcessPaymentNullAddr() {
    CODPaymentStrategy strategy = new CODPaymentStrategy();
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("deliveryFee", "10000");

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }

  @Test
  void testProcessPaymentEmptyAdd() {
    CODPaymentStrategy strategy = new CODPaymentStrategy();
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("address", "");
    paymentData.put("deliveryFee", "10000");

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }

  @Test
  void testProcessPaymentNullDeliveryFee() {
    CODPaymentStrategy strategy = new CODPaymentStrategy();
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("address", "Depok");

    String result = strategy.processPayment(paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), result);
  }
}
