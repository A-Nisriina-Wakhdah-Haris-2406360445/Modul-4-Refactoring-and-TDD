package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testCreateOrderPage() throws Exception {
    mockMvc.perform(get("/order/create"))
        .andExpect(status().isOk())
        .andExpect(view().name("CreateOrder"));
  }

  @Test
  void testHistoryPage() throws Exception {
    mockMvc.perform(get("/order/history"))
        .andExpect(status().isOk())
        .andExpect(view().name("history"));
  }

  @Test
  void testHistoryPost() throws Exception {
    mockMvc.perform(post("/order/history")
            .param("name", "Budi"))
        .andExpect(status().isOk())
        .andExpect(view().name("history-result"));
  }
}