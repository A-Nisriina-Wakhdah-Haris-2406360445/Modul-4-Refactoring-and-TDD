package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

  private List<Product> products;

  private PaymentRepository paymentRepository;
  private Order order;

  @BeforeEach
  void setUp() {
    paymentRepository = new PaymentRepository();

    Product product = new Product();
    product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
    product.setProductName("Sampo Cap Bambang");
    product.setProductQuantity(2);
    products.add(product);

    Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
        products, 1708560000L, "Safira Sudrajat");
  }

  @Test
  void testSavePayment() {

    Map<String, String> data = new HashMap<>();
    data.put("voucherCode", "ESHOP1234ABC5678");

    Payment payment = new Payment(order, "VOUCHER_CODE", data);

    Payment saved = paymentRepository.save(payment);

    assertEquals(payment, saved);
    assertEquals(payment, paymentRepository.findById(payment.getId()));
  }

  @Test
  void testFindById() {

    Payment payment = new Payment(order, "VOUCHER_CODE", new HashMap<>());
    paymentRepository.save(payment);

    Payment result = paymentRepository.findById(payment.getId());

    assertNotNull(result);
    assertEquals(payment.getId(), result.getId());
  }

  @Test
  void testFindAll() {

    Payment payment1 = new Payment(order, "VOUCHER_CODE", new HashMap<>());
    Payment payment2 = new Payment(order, "BANK_TRANSFER", new HashMap<>());

    paymentRepository.save(payment1);
    paymentRepository.save(payment2);

    Map<String, Payment> allPayments = paymentRepository.findAll();

    assertEquals(2, allPayments.size());
    assertTrue(allPayments.containsKey(payment1.getId()));
    assertTrue(allPayments.containsKey(payment2.getId()));
  }

  @Test
  void testFindByIdIfNotFound() {

    Payment result = paymentRepository.findById("invalid-id");
    assertNull(result);
  }

  @Test
  void testFindAllIfEmptyRepository() {

    Map<String, Payment> allPayments = paymentRepository.findAll();
    assertTrue(allPayments.isEmpty());
  }



}
