package id.ac.ui.cs.advprog.eshop.service.car;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarCRUD;
import id.ac.ui.cs.advprog.eshop.repository.CarReadOnly;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarServiceRead, CarServiceCRUD {

  private final CarCRUD carCRUD;
  private final CarReadOnly carRead;

  public CarServiceImpl(CarCRUD carCRUD, CarReadOnly carRead) {
    this.carCRUD = carCRUD;
    this.carRead = carRead;
  }

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
    return carRead.findById(carId);
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
