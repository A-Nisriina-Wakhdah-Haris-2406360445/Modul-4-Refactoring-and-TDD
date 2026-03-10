package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @MockBean
  private PaymentService paymentService;

  private Order order;
  private Payment payment;

  @BeforeEach
  void setUp() {

    order = mock(Order.class);
    payment = mock(Payment.class);

    when(payment.getId()).thenReturn("payment-1");
  }

  @Test
  void testCreateOrderPage() throws Exception {

    mockMvc.perform(get("/order/create"))
        .andExpect(status().isOk())
        .andExpect(view().name("CreateOrder"));
  }

  @Test
  void testHistoryForm() throws Exception {

    mockMvc.perform(get("/order/history"))
        .andExpect(status().isOk())
        .andExpect(view().name("OrderHistory"));
  }

  @Test
  void testHistoryResult() throws Exception {

    when(orderService.findAllByAuthor("Budi"))
        .thenReturn(List.of(order));

    mockMvc.perform(post("/order/history")
            .param("name", "Budi"))
        .andExpect(status().isOk())
        .andExpect(view().name("HistoryResult"))
        .andExpect(model().attributeExists("orders"));

    verify(orderService).findAllByAuthor("Budi");
  }

  @Test
  void testPayOrderPage() throws Exception {

    when(orderService.findById("1")).thenReturn(order);

    mockMvc.perform(get("/order/pay/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("PayOrder"))
        .andExpect(model().attributeExists("order"));

    verify(orderService).findById("1");
  }

  @Test
  void testPayOrder() throws Exception {

    when(orderService.findById("1")).thenReturn(order);
    when(paymentService.addPayment(eq(order), eq(PaymentMethod.COD.getValue()), anyMap()))
        .thenReturn(payment);

    mockMvc.perform(post("/order/pay/1")
            .param("method", PaymentMethod.COD.getValue()))
        .andExpect(status().isOk())
        .andExpect(view().name("PaymentResult"))
        .andExpect(model().attributeExists("paymentId"));

    verify(orderService).findById("1");
    verify(paymentService).addPayment(eq(order), eq(PaymentMethod.COD.getValue()), anyMap());
  }
}