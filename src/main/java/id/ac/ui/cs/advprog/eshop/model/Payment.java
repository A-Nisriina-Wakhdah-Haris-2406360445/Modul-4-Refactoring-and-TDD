package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {

  private String id;
  private String method;
  private String status;
  private Map<String, String> paymentData;
  private Order order;

  public Payment(Order order, String method, Map<String, String> paymentData) {
    this.id = UUID.randomUUID().toString();
    this.order = order;
    this.method = method;
    this.paymentData = paymentData;
  }

  public void setMethod(String method) {
    if(PaymentMethod.contains(method)) {
      this.method = method;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setStatus(String status) {
    if (PaymentStatus.contains(status)) {
      this.status = status;
    } else {
      throw new IllegalArgumentException();
    }
  }
}
