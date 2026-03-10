package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
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

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PaymentService paymentService;

  private Payment payment;

  @BeforeEach
  void setUp() {

    Order order = mock(Order.class);

    payment = new Payment(order, PaymentMethod.COD.getValue(), Map.of());

  }

  @Test
  void testPaymentDetailForm() throws Exception {

    mockMvc.perform(get("/payment/detail"))
        .andExpect(status().isOk())
        .andExpect(view().name("PaymentDetail"));
  }

  @Test
  void testPaymentDetail() throws Exception {

    when(paymentService.getPayment("1")).thenReturn(payment);

    mockMvc.perform(get("/payment/detail/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("DetailResult"))
        .andExpect(model().attributeExists("payment"));

    verify(paymentService).getPayment("1");
  }

  @Test
  void testAdminList() throws Exception {

    when(paymentService.getAllPayments()).thenReturn(List.of(payment));

    mockMvc.perform(get("/payment/admin/list"))
        .andExpect(status().isOk())
        .andExpect(view().name("AdminList"))
        .andExpect(model().attributeExists("payments"));

    verify(paymentService).getAllPayments();
  }

  @Test
  void testAdminDetail() throws Exception {

    when(paymentService.getPayment("1")).thenReturn(payment);

    mockMvc.perform(get("/payment/admin/detail/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("AdminDetail"))
        .andExpect(model().attributeExists("payment"));

    verify(paymentService).getPayment("1");
  }

  @Test
  void testSetStatus() throws Exception {

    when(paymentService.getPayment("1")).thenReturn(payment);

    mockMvc.perform(post("/payment/admin/set-status/1")
            .param("status", "SUCCESS"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:AdminList"));

    verify(paymentService).getPayment("1");
    verify(paymentService).setStatus(payment, "SUCCESS");
  }
}