package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarRepositoryImplTest {

  @InjectMocks
  CarRepository carRepository;

  @Test
  void testCreateCar() {

    // Set id to null
    Car car = new Car();
    car.setCarId(null);
    car.setCarName("Camry");
    car.setCarColor("Black");
    car.setCarQuantity(5);
    carRepository.create(car);

    Car newCar = new Car();
    car.setCarId("123");
    car.setCarName("Kijang");
    car.setCarColor("Green");
    car.setCarQuantity(12);
    carRepository.create(newCar);
  }

  @Test
  void testFindAllIfEmpty() {
    Iterator<Car> carIterator = carRepository.findAll();
    assertFalse(carIterator.hasNext());
  }

  @Test
  void testFindAllIfMoreThanOneCars() {
    Car car1 = new Car();
    car1.setCarId("123");
    car1.setCarName("Honda");
    car1.setCarQuantity(12);
    car1.setCarColor("White");
    carRepository.create(car1);

    Car car2 = new Car();
    car2.setCarId("123-001");
    car2.setCarName("Honda Mobilio");
    car2.setCarQuantity(2);
    car2.setCarColor("Neon");
    carRepository.create(car2);

    Iterator<Car> carIterator = carRepository.findAll();
    assertTrue(carIterator.hasNext());

    // positive case
    Car savedCar = carIterator.next();
    assertEquals(car1.getCarColor(), savedCar.getCarColor());
    assertNotEquals(car2.getCarColor(), savedCar.getCarColor());

    savedCar = carIterator.next();
    assertEquals(car2.getCarName(), savedCar.getCarName());
    assertNotEquals(car1.getCarName(), savedCar.getCarName());
  }

  @Test
  void testFindById_returnCar() {
    Car car = new Car();
    car.setCarId("123");
    car.setCarName("Honda");
    car.setCarQuantity(12);
    car.setCarColor("White");
    carRepository.create(car);

    assertEquals(car, carRepository.findById(car.getCarId()));
  }

  @Test
  void testFindById_emptyRepo() {
    assertNull(carRepository.findById("0123"));
  }

  @Test
  void testFindById_notFound() {
    Car car = new Car();
    car.setCarId("123");
    car.setCarName("Honda");
    car.setCarQuantity(12);
    car.setCarColor("White");
    carRepository.create(car);

    assertNull(carRepository.findById("123-100"));
  }

  @Test
  void testUpdate_emptyRepo() {
    Car car = new Car();
    car.setCarName("Limousin");

    assertNull(carRepository.update("123-00", car));
  }

  @Test
  void testUpdate() {
    Car car = new Car();
    car.setCarId("123-00");
    car.setCarName("Wuling");
    car.setCarColor("Blue");
    car.setCarQuantity(12);
    carRepository.create(car);

    Car updatedCar = new Car();
    updatedCar.setCarId("123-00");
    updatedCar.setCarName("Wuling Air EV");
    updatedCar.setCarColor("Pink");
    updatedCar.setCarQuantity(10);
    carRepository.update(updatedCar.getCarId(), updatedCar);

    assertEquals("Wuling Air EV", updatedCar.getCarName());
    assertEquals("Pink", updatedCar.getCarColor());
  }

  @Test
  void testUpdate_wrongIdCar() {
    Car car = new Car();
    car.setCarId("000-001");
    car.setCarName("HR-V");
    car.setCarColor("Gray");
    car.setCarQuantity(5);
    carRepository.create(car);

    Car updated = new Car();
    updated.setCarName("Cherry");
    updated.setCarColor("Red");
    updated.setCarQuantity(2);
    carRepository.update("000-000", updated);

    assertNotEquals("Cherry", carRepository.findById("000-001").getCarName());
  }

  @Test
  void testDelete() {
    Car car = new Car();
    car.setCarId("122-447");
    car.setCarName("Cherry");
    car.setCarColor("Red");
    car.setCarQuantity(2);
    carRepository.create(car);

    // delete car
    carRepository.delete("122-447");
    assertNull(carRepository.findById("122-447"));
    Iterator<Car> carIterator = carRepository.findAll();
    assertFalse(carIterator.hasNext());

    // negative case
    Car car2 = new Car();
    car2.setCarId("000-001");
    car2.setCarName("HR-V");
    car2.setCarColor("Gray");
    car2.setCarQuantity(5);
    carRepository.create(car2);

    carRepository.delete("000-000");
    assertNotNull(carRepository.findById("000-001"));
    Iterator<Car> iteratorAfterWrongDeletion = carRepository.findAll();
    assertTrue(iteratorAfterWrongDeletion.hasNext());

  }




}
