package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarCRUD {
  Car create(Car car);
  Car update(String id, Car car);
  void delete(String id);
}
