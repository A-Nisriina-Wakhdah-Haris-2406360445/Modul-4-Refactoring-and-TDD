package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceCRUD;
import id.ac.ui.cs.advprog.eshop.service.CarServiceRead;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

  private final CarServiceRead carServiceRead;
  private final CarServiceCRUD carServiceCRUD;

  public CarController(CarServiceRead carServiceRead, CarServiceCRUD carServiceCRUD) {
    this.carServiceRead = carServiceRead;
    this.carServiceCRUD = carServiceCRUD;
  }

  @GetMapping("/createCar")
  public String createCarPage(Model model) {
    Car car = new Car();
    model.addAttribute("car", car);
    return "createCar";
  }

  @PostMapping("/createCar")
  public String createCarPost(@ModelAttribute Car car, Model model) {
    carServiceCRUD.create(car);
    return "redirect:listCar";
  }

  @GetMapping("/listCar")
  public String carListPage(Model model) {
    List<Car> allCars = carServiceRead.findAll();
    model.addAttribute("cars", allCars);
    return "listCar";
  }

  @GetMapping("/editCar/{carId}")
  public String editCarPage(@PathVariable String carId, Model model) {
    Car car = carServiceRead.findById(carId);
    model.addAttribute("car", car);
    return "editCar";
  }

  @PostMapping("/editCar")
  public String editCarPost(@ModelAttribute Car car, Model model) {
    System.out.println(car.getCarId());
    carServiceCRUD.update(car.getCarId(), car);
    return "redirect:listCar";
  }

  @PostMapping("/deleteCar")
  public String deleteCar(@RequestParam("carId") String carId) {
    carServiceCRUD.deleteCarById(carId);
    return "redirect:listCar";
  }

}
