package id.ac.ui.cs.advprog.eshop.service.car;
import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.List;

public interface CarServiceRead {
  List<Car> findAll();
  Car findById(String carId);
}
