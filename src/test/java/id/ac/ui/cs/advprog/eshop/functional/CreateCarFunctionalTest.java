package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateCarFunctionalTest {

  @LocalServerPort
  private int serverPort;

  @Value("${app.baseUrl:http://localhost}")
  private String testBaseUrl;

  private String baseUrl;

  @BeforeEach
  void setUpTest() {
    baseUrl = String.format("%s:%d/car/createCar", testBaseUrl, serverPort);
  }

  @Test
  void titleCreateCar_isCorrect(ChromeDriver driver) {
    driver.get(baseUrl);
    String titlePage = driver.getTitle();
    assertEquals("Create New Car", titlePage);
  }

  @Test
  void welcomeMessage_createCar_isCorrect(ChromeDriver driver) {
    driver.get(baseUrl);
    String welcomeMessage = driver.findElement(By.tagName("h3")).getText();
    assertEquals("Create New Car", welcomeMessage);
  }

  @Test
  void testInputName(ChromeDriver driver) {
    driver.get(baseUrl);

    WebElement inputName = driver.findElement(By.name("carName"));
    inputName.clear();

    // input
    String carName = "Innova";
    inputName.sendKeys(carName);

    // verify
    String data = inputName.getAttribute("value");
    assertEquals(carName, data);

    // clear field
    inputName.clear();
    data = inputName.getAttribute("value");
    assertEquals("", data);
  }

  @Test
  void testInputColor(ChromeDriver driver) {
    driver.get(baseUrl);

    WebElement inputColor = driver.findElement(By.name("carColor"));
    inputColor.clear();

    // input
    String carColor = "Black";
    inputColor.sendKeys(carColor);

    // verify
    String data = inputColor.getAttribute("value");
    assertEquals(carColor, data);

    // clear input
    inputColor.clear();
    data = inputColor.getAttribute("value");
    assertEquals("", data);
  }

  @Test
  void testInputQuantity(ChromeDriver driver) {
    driver.get(baseUrl);

    WebElement inputQuantity = driver.findElement(By.name("carQuantity"));
    inputQuantity.clear();

    // input
    int carQuantity = 5;
    inputQuantity.sendKeys(Integer.toString(carQuantity));

    // verify
    String data = inputQuantity.getAttribute("value");
    assertEquals(Integer.toString(carQuantity), data);

    // clear input
    inputQuantity.clear();
    data = inputQuantity.getAttribute("value");
    assertEquals("", data);
  }

  @Test
  void testCreateCar(ChromeDriver driver) {
    driver.get(baseUrl);

    // fill car name
    WebElement inputName = driver.findElement(By.name("carName"));
    inputName.clear();
    String carName = "Ferrari";
    inputName.sendKeys(carName);

    // fill car color
    WebElement inputColor = driver.findElement(By.name("carColor"));
    inputColor.clear();
    String carColor = "Red";
    inputColor.sendKeys(carColor);

    // fill car quantity
    WebElement inputQuantity = driver.findElement(By.name("carQuantity"));
    inputQuantity.clear();
    int carQuantity = 2;
    inputQuantity.sendKeys(String.valueOf(carQuantity));

    // submit input
    WebElement submitBtn = driver.findElement(By.tagName("button"));
    submitBtn.click();

    // verify redirect
    String currUrl = driver.getCurrentUrl();
    assertEquals(String.format("%s:%d/car/listCar", testBaseUrl, serverPort), currUrl);

    // verify the existence of product in the list
    String data = driver.getPageSource();
    assertEquals(true, data.contains(carName));
    assertEquals(true, data.contains(carColor));
    assertEquals(true, data.contains(String.valueOf(carQuantity)));

  }
}
