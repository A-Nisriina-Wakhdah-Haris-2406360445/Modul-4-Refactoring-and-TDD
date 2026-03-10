package id.ac.ui.cs.advprog.eshop.functional.order;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class OrderHistoryFunctionalTest {

  @LocalServerPort
  private int serverPort;

  @Value("${app.baseUrl:http://localhost}")
  private String testBaseUrl;

  private String baseUrl;

  @BeforeEach
  void setUp() {
    baseUrl = String.format("%s:%d/order/history", testBaseUrl, serverPort);
  }

  @Test
  void titleOrderHistory_isCorrect(ChromeDriver driver) {
    driver.get(baseUrl);
    String titlePage = driver.getTitle();
    assertEquals("Search Order History", titlePage);
  }
}