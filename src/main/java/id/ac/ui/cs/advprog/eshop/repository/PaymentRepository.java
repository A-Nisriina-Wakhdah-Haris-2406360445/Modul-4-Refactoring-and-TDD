package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentRepository {
  private final Map<String, Payment> paymentData = new HashMap<>();

  public Payment save(Payment payment){
    return null;
  }

  public Payment findById(String id) {
    return null;
  }

  public Map<String, Payment> findAll() {
    return null;
  }
}
