package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
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

  public void setStatus(String status) {
    String[] statusList = {"SUCCESS", "REJECTED"};
    if(Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))) {
      throw new IllegalArgumentException();
    } else {
      this.status = status;
    }
  }
}
