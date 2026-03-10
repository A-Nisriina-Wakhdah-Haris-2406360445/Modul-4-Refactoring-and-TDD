package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BankTransferPaymentStrategy implements PaymentStrategy {

  @Override
  public String processPayment(Map<String, String> paymentData) {

    String bankName = paymentData.get("bankName");
    String referenceCode = paymentData.get("referenceCode");

    if (bankName == null || bankName.isEmpty()) {
      return PaymentStatus.REJECTED.getValue();
    }

    if (referenceCode == null || referenceCode.isEmpty()) {
      return PaymentStatus.REJECTED.getValue();
    }

    return PaymentStatus.SUCCESS.getValue();
  }
}
