package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarCRUD;
import id.ac.ui.cs.advprog.eshop.repository.CarReadOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarServiceRead, CarServiceCRUD {
  @Autowired
  private CarCRUD carCRUD;

  @Autowired
  private CarReadOnly carRead;

  @Override
  public Car create(Car car) {
    if (car.getCarId() == null) {
      car.setCarId(UUID.randomUUID().toString());
    }
    carCRUD.create(car);
    return car;
  }

  @Override
  public List<Car> findAll() {
    Iterator<Car> carIterator = carRead.findAll();
    List<Car> allCar = new ArrayList<>();
    carIterator.forEachRemaining(allCar::add);
    return allCar;
  }

  @Override
  public Car findById(String carId) {
    Car car = carRead.findById(carId);
    return car;
  }

  @Override
  public void update(String carId, Car car) {
    carCRUD.update(carId, car);
  }

  @Override
  public void deleteCarById(String carId) {
    carCRUD.delete(carId);
  }
}
