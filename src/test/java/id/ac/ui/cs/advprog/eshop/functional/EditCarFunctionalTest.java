package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class EditCarFunctionalTest {

  @LocalServerPort
  private int serverPort;

  @Value("${app.baseUrl:http://localhost}")
  private String testBaseUrl;

  private String baseUrl;

  @Autowired
  private CarRepository carRepository;

  @BeforeEach
  void setUpTest() {
    Car car = new Car();
    car.setCarId("test-id-1");
    car.setCarName("Pajero");
    car.setCarQuantity(1);
    carRepository.create(car);

    baseUrl = String.format("%s:%d/car/editCar/%s", testBaseUrl, serverPort, car.getCarId());
  }

  @Test
  void titleEditCar_isCorrect(ChromeDriver driver) {
    driver.get(baseUrl);
    String titlePage = driver.getTitle();
    assertEquals("Edit Car", titlePage);
  }

  @Test
  void welcomeMessage_EditCar_isCorrect(ChromeDriver driver) {
    driver.get(baseUrl);
    String welcomeMessage = driver.findElement(By.tagName("h1")).getText();
    assertEquals("Edit Car", welcomeMessage);
  }

  @Test
  void testInputName(ChromeDriver driver) {
    driver.get(baseUrl);

    WebElement inputName = driver.findElement(By.name("carName"));
    inputName.clear();

    // input
    String newCar = "Wuling Air EV";
    inputName.sendKeys(newCar);

    // verify
    String data = inputName.getAttribute("value");
    assertEquals(newCar, data);

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
    String newCar = "Blue";
    inputColor.sendKeys(newCar);

    // verify
    String data = inputColor.getAttribute("value");
    assertEquals(newCar, data);

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
    int newQuantity = 5;
    inputQuantity.sendKeys(Integer.toString(newQuantity));

    // verify
    String data = inputQuantity.getAttribute("value");
    assertEquals(Integer.toString(newQuantity), data);

    // clear input
    inputQuantity.clear();
    data = inputQuantity.getAttribute("value");
    assertEquals("", data);
  }

  @Test
  void testEditSuccess(ChromeDriver driver) {
    driver.get(baseUrl);

    WebElement nameField = driver.findElement(By.name("carName"));
    WebElement colorField = driver.findElement(By.name("carColor"));
    WebElement quantityField = driver.findElement(By.name("carQuantity"));

    // edit car's name
    nameField.clear();
    nameField.sendKeys("Wuling Air Ev");

    // edit car's color
    colorField.clear();
    colorField.sendKeys("Baby blue");

    // edit car's quantity
    quantityField.clear();
    quantityField.sendKeys("3");

    driver.findElement(By.tagName("button")).click();

    // make sure the new attribute is updated on the list
    driver.get(String.format("%s:%d/car/listCar", testBaseUrl, serverPort));

    String page = driver.getPageSource();
    assertEquals(true, page.contains("Wuling Air Ev"));
    assertEquals(true, page.contains("Baby blue"));
    assertEquals(true, page.contains(Integer.toString(3)));



  }
}
