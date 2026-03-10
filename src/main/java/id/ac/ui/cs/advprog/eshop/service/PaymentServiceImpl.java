package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService{

  private final PaymentRepository paymentRepository;
  private final Map<String, PaymentStrategy> strategies;

  public PaymentServiceImpl(PaymentRepository paymentRepository) {

    this.paymentRepository = paymentRepository;

    strategies = new HashMap<>();
    strategies.put(PaymentMethod.VOUCHER_CODE.getValue(), new VoucherPaymentStrategy());
    strategies.put(PaymentMethod.COD.getValue(), new CODPaymentStrategy());
  }

  @Override
  public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

    Payment payment = new Payment(order, method, paymentData);

    PaymentStrategy strategy = strategies.get(method);

    if (strategy == null) {
      payment.setStatus(PaymentStatus.REJECTED.getValue());
    } else {
      String status = strategy.processPayment(paymentData);
      payment.setStatus(status);
    }

    paymentRepository.save(payment);

    return payment;
  }

  @Override
  public Payment setStatus(Payment payment, String status) {
    payment.setStatus(status);

    if(PaymentStatus.SUCCESS.getValue().equals(status)) {
      payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
    }

    if (PaymentStatus.REJECTED.getValue().equals(status)) {
      payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
    }
    return payment;
  }

  @Override
  public Payment getPayment(String paymentId) {
    return paymentRepository.findById(paymentId);
  }

  @Override
  public List<Payment> getAllPayments() {
    return new ArrayList<>(paymentRepository.findAll().values());
  }

}
