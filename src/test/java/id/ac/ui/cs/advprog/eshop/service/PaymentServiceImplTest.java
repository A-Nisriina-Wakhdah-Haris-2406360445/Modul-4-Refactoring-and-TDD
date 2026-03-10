package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

  @Mock
  PaymentRepository paymentRepository;

  @InjectMocks
  PaymentServiceImpl paymentService;

  List<Order> orders;

  @BeforeEach
  void setUp() {
    List<Product> products = new ArrayList<>();
    Product product1 = new Product();
    product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
    product1.setProductName("Sampo Cap Bambang");
    product1.setProductQuantity(2);
    products.add(product1);

    orders = new ArrayList<>();
    Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
        products, 1708560000L, "Safira Sudrajat");
    orders.add(order1);
    Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
        products, 1708570000L, "Safira Sudrajat");
    orders.add(order2);
  }

  @Test
  void testAddPaymentVoucherSuccess() {
    Order order = orders.get(1);
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "ESHOP1234ABC5678");

    Payment payment = paymentService.addPayment(order, PaymentMethod.VOUCHER_CODE.getValue(),
        paymentData);

    assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    verify(paymentRepository, times(1)).save(payment);
  }

  @Test
  void testAddPaymentCODSuccess() {
    Order order = orders.get(0);
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("address", "Bekasi");
    paymentData.put("deliveryFee", "6000");

    Payment payment = paymentService.addPayment(order, PaymentMethod.VOUCHER_CODE.getValue(),
        paymentData);

    assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    verify(paymentRepository, times(1)).save(payment);
  }

  @Test
  void testAddPaymentBankTFSuccess() {
    Order order = orders.get(0);
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("bankName", "BCA");
    paymentData.put("referenceCode", "TRX123");

    Payment payment = paymentService.addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(),
        paymentData);

    assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    verify(paymentRepository).save(payment);
  }

  @Test
  void testAddPaymentInvalidMethod() {
    Order order = orders.get(1);
    Map<String, String> paymentData = new HashMap<>();

    Payment payment = paymentService.addPayment(order, "INVALID_METHOD",
        paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    verify(paymentRepository, times(1)).save(payment);
  }

  @Test
  void testAddPaymentInvalidVoucher() {
    Order order = orders.get(1);

    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "INVALID");

    Payment payment = paymentService.addPayment(order, PaymentMethod.VOUCHER_CODE.getValue(),
        paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testSetStatusSuccess() {
    Order order = orders.get(1);
    Payment payment = new Payment(order, PaymentMethod.BANK_TRANSFER.getValue(),
        new HashMap<>());

    paymentService.setStatus(payment, "SUCCESS");

    assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    assertEquals(OrderStatus.SUCCESS.getValue(), payment.getOrder().getStatus());
  }

  @Test
  void testSetStatusRejected() {
    Order order = orders.get(1);
    Payment payment = new Payment(order, PaymentMethod.BANK_TRANSFER.getValue(),
        new HashMap<>());
    paymentService.setStatus(payment, "REJECTED");

    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    assertEquals(OrderStatus.FAILED.getValue(), payment.getStatus());
  }

  @Test
  void testGetPaymentById() {
    Payment payment = mock(Payment.class);
    when(paymentRepository.findById("1")).thenReturn(payment);

    Payment result = paymentService.getPayment("1");
    assertEquals(payment, result);
  }

  @Test
  void testGetAllPayments() {
    Map<String, Payment> payments = new HashMap<>();
    when(paymentRepository.findAll()).thenReturn(payments);

    Payment result = paymentService.getAllPayments();

    assertEquals(payments, result);
  }
}
