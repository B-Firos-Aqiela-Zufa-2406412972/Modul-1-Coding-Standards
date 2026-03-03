package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/create") // Standardized path
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar"; // This must match your HTML filename exactly!
    }

    @PostMapping("/create") // Standardized path
    public String createCarPost(@ModelAttribute Car car, Model model){
        carService.create(car);
        return "redirect:list"; // Fixes the 404 by redirecting to the new /list path
    }

    @GetMapping("/list") // Fixes the 404 error path!
    public String carListPage(Model model){
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "carList"; // This must match your HTML filename exactly!
    }

    @GetMapping("/edit/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "editCar"; // This must match your HTML filename exactly!
    }

    @PostMapping("/edit") // Standardized path
    public String editCarPost(@ModelAttribute Car car, Model model) {
        // Updated to use getId() since Car now inherits from Product
        carService.update(car);
        return "redirect:list";
    }

    @PostMapping("/delete") // Standardized path
    public String deleteCar(@RequestParam("carId") String carId) {
        carService.deleteCarById(carId);
        return "redirect:list";
    }
}