package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarCRUD;
import id.ac.ui.cs.advprog.eshop.repository.CarReadOnly;
import id.ac.ui.cs.advprog.eshop.service.car.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

  @Mock
  CarReadOnly carReadOnly;

  @Mock
  CarCRUD carCRUD;

  @InjectMocks
  CarServiceImpl carService;

  @Test
  void testCreateWithoutId() {
    Car car = new Car();
    car.setCarQuantity(10);
    car.setCarColor("Red");
    car.setCarName("Cherry");

    Car createdCar = carService.create(car);
    assertNotNull(createdCar.getCarId());
  }

  @Test
  void testCreateWithId() {
    Car car = new Car();
    car.setCarQuantity(4);
    car.setCarColor("Red");
    car.setCarName("Ferrari");
    car.setCarId("001-110");

    Car createdCar = carService.create(car);
    assertEquals("001-110", createdCar.getCarId());
  }

  @Test
  void testFindAll() {
    Car car = new Car();
    car.setCarName("Toyota");
    car.setCarColor("Black");
    car.setCarQuantity(3);

    Car car2 = new Car();
    car2.setCarName("Kijang");
    car2.setCarColor("Green");
    car2.setCarQuantity(2);

    Iterator<Car> iterator = Arrays.asList(car, car2).iterator();
    when(carReadOnly.findAll()).thenReturn((iterator));

    List<Car> result = carService.findAll();
    assertEquals(2, result.size());
  }

  @Test
  void testFindById() {
    Car car = new Car();
    car.setCarName("Limousin");
    car.setCarColor("Silver");
    car.setCarQuantity(12);

    when(carReadOnly.findById(car.getCarId())).thenReturn(car);

    Car result = carService.findById(car.getCarId());
    assertEquals(car, result);
  }

  @Test
  void testUpdate() {
    Car car = new Car();
    car.setCarName("Suzuki");
    car.setCarColor("White");
    car.setCarQuantity(5);

    carService.update(car.getCarId(), car);
    verify(carCRUD, times(1)).update(car.getCarId(), car);
  }

  @Test
  void testDeleteById() {
    carService.deleteCarById("110");
    verify(carCRUD, times(1)).delete("110");
  }
}
