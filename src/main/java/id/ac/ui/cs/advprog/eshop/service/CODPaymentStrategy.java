package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class CODPaymentStrategy implements PaymentStrategy {

  @Override
  public String processPayment(Map<String, String> paymentData) {
    String addres = paymentData.get("address");
    String deliveryFee = paymentData.get("deliveryFee");

    if (addres == null || addres.isEmpty()) {
      return PaymentStatus.REJECTED.getValue();
    }

    if(deliveryFee == null || deliveryFee.isEmpty()) {
      return PaymentStatus.REJECTED.getValue();
    }

    return PaymentStatus.SUCCESS.getValue();
  }
}
