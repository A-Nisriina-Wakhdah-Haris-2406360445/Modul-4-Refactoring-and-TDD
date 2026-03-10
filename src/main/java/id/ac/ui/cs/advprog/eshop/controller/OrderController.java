package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private PaymentService paymentService;

  @GetMapping("/create")
  public String createOrderPage() {
    return "create";
  }

  @GetMapping("/history")
  public String historyForm() {
    return "history";
  }

  @PostMapping("/history")
  public String historyResult(@RequestParam String name, Model model) {

    List<Order> orders = orderService.findAllByAuthor(name);
    model.addAttribute("orders", orders);
    return "history-result";
  }

  @GetMapping("/pay/{orderId}")
  public String payOrderPage(@PathVariable String orderId, Model model) {

    Order order = orderService.findById(orderId);
    model.addAttribute("order", order);
    return "pay";
  }

  @PostMapping("/pay/{orderId}")
  public String payOrder(
      @PathVariable String orderId,
      @RequestParam String method,
      Model model) {

    Order order = orderService.findById(orderId);
    Map<String, String> paymentData = new HashMap<>();
    Payment payment = paymentService.addPayment(order, method, paymentData);

    model.addAttribute("paymentId", payment.getId());
    return "payment-result";
  }
}