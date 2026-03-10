package id.ac.ui.cs.advprog.eshop.service;

import java.util.Map;

public interface PaymentStrategy {
  String processPayment(Map<String, String> paymentData);
}
