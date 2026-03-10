package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PaymentTest {

  private List<Product> products;
  List<Order> orders;

  @Mock
  OrderRepository orderRepository;

  @BeforeEach
  void setUp() {
    this.products = new ArrayList<>();
    this.orders = new ArrayList<>();

    Product product1 = new Product();
    product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
    product1.setProductName("Sampo Cap Bambang");
    product1.setProductQuantity(2);

    Product product2 = new Product();
    product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
    product2.setProductName("Sampo Cap Usep");
    product2.setProductQuantity(1);
    this.products.add(product1);
    this.products.add(product2);

    Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
        products, 1708560000L, "Safira Sudrajat");
    orders.add(order1);
    Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
        products, 1708570000L, "Safira Sudrajat");
    orders.add(order2);
  }

  @Test
  void testCreatePayment() {
    Order order = orders.get(1);
    orderRepository.save(order);
    Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(),
        order.getAuthor(), OrderStatus.WAITING_PAYMENT.getValue());
    Order result = orderRepository.save(newOrder);

    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucheCode", "ESHOP1234ABC5678");

    Payment payment = new Payment(result, "VOUCHER_CODE", paymentData);

    assertNotNull(payment.getId());
    assertEquals("VOUCHER_CODE", payment.getMethod());
    assertEquals(paymentData, payment.getPaymentData());
    assertEquals(result, payment.getOrder());
  }

  @Test
  void createPaymentIfNullPaymentData() {
    Order order = orders.get(1);
    orderRepository.save(order);

    Payment payment = new Payment(order, "VOUCHER_CODE", null);

    assertNotNull(payment.getId());
    assertEquals("VOUCHER_CODE", payment.getMethod());
    assertNull(payment.getPaymentData());
    assertEquals(order, payment.getOrder());
  }

  @Test
  void paymentIdShouldBeUnique() {
    Order order = orders.get(0);

    Payment payment1 = new Payment(order, "VOUCHER_CODE", new HashMap<>());
    Payment payment2 = new Payment(order, "VOUCHER_CODE", new HashMap<>());

    assertNotEquals(payment1.getId(), payment2.getId());
  }

  @Test
  void testSetOrderInvalidMethod() {
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "ESHOP1234ABC5678");

    assertThrows(IllegalArgumentException.class, () ->{
      Payment payment = new Payment(orders.get(1), PaymentMethod.COD.getValue(),
          paymentData);
      payment.setMethod("BUZZ");
    });
  }

  @Test
  void testSetPaymentMethodToCOD() {
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "ESHOP1234ABC5678");
    Payment payment = new Payment(orders.get(1), PaymentMethod.COD.getValue(),paymentData);
    payment.setStatus(PaymentStatus.SUCCESS.getValue());
    assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
  }

  @Test
  void testSetOrderInvalidStatus() {
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "ESHOP1234ABC5678");

    assertThrows(IllegalArgumentException.class, () ->{
      Payment payment = new Payment(orders.get(1), "VOUCHER_CODE",paymentData);
      payment.setStatus("BUZZ");
    });
  }

  @Test
  void testSetStatusToSuccess() {
    Map<String, String> paymentData = new HashMap<>();
    paymentData.put("voucherCode", "ESHOP1234ABC5678");
    Payment payment = new Payment(orders.get(1), "VOUCHER_CODE",paymentData);
    payment.setStatus(PaymentStatus.SUCCESS.getValue());
    assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
  }

}
