package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class VoucherPaymentStrategy implements PaymentStrategy{

  @Override
  public String processPayment(Map<String, String> paymentData) {
    String code = paymentData.get("voucherCode");

    if(code == null) {
      return PaymentStatus.REJECTED.getValue();
    }

    if(code.length() != 16) {
      return PaymentStatus.REJECTED.getValue();
    }

    if (!code.startsWith("ESHOP")) {
      return PaymentStatus.REJECTED.getValue();
    }

    int digitCount = 0;
    for(char c : code.toCharArray()) {
      if (Character.isDigit(c)) {
        digitCount++;
      }
    }

    return digitCount == 8 ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
  }
}
