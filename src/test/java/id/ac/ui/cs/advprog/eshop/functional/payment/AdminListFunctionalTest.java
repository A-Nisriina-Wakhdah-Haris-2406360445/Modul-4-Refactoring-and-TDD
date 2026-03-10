package id.ac.ui.cs.advprog.eshop.functional.payment;

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
class AdminListFunctionalTest {

  @LocalServerPort
  private int serverPort;

  @Value("${app.baseUrl:http://localhost}")
  private String testBaseUrl;

  private String baseUrl;

  @BeforeEach
  void setUp() {
    baseUrl = String.format("%s:%d/payment/admin/list", testBaseUrl, serverPort);
  }

  @Test
  void titleAdminList_isCorrect(ChromeDriver driver) {
    driver.get(baseUrl);
    String titlePage = driver.getTitle();
    assertEquals("Admin Payment List", titlePage);
  }
}