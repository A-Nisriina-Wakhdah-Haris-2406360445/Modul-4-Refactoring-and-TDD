package id.ac.ui.cs.advprog.eshop.controller;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.advprog.eshop.service.CarServiceCRUD;
import id.ac.ui.cs.advprog.eshop.service.CarServiceRead;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CarServiceRead serviceRead;

  @MockBean
  private CarServiceCRUD serviceCRUD;

  @Test
  void testCreateCarPage() throws Exception {
    mockMvc.perform(get("/car/createCar"))
        .andExpect(status().isOk())
        .andExpect(view().name("createCar"))
        .andExpect(model().attributeExists("car"));

  }

  @Test
  void testCreateCarPost() throws Exception {
    mockMvc.perform(post("/car/createCar")
        .param("name", "Innova")
        .param("color", "White")
        .param("quantity", "5"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("listCar"));
  }

  @Test
  void testCarListPage() throws Exception {
    List<Car> carList = new ArrayList<>();
    carList.add(new Car());

    when(serviceRead.findAll()).thenReturn(carList);

    mockMvc.perform(get("/car/listCar"))
        .andExpect(status().isOk())
        .andExpect(view().name("listCar"))
        .andExpect(model().attributeExists("cars"));
  }

  @Test
  void testEditCarPage() throws Exception {
    Car car = new Car();
    car.setCarId("110-001");

    when(serviceRead.findById("110-001")).thenReturn(car);

    mockMvc.perform(get("/car/editCar/110-001"))
        .andExpect(status().isOk())
        .andExpect(view().name("editCar"))
        .andExpect(model().attributeExists("car"));
  }

  @Test
  void testEditCarPost() throws Exception {
    mockMvc.perform(post("/car/editCar")
        .param("id", "1")
        .param("name", "Innova")
        .param("color", "Black")
        .param("quantity", "2"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("listCar"));
  }

  @Test
  void testDeleteCar() throws Exception {
    mockMvc.perform(post("/car/deleteCar")
            .param("carId", "110-001"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("listCar"));
  }

}
