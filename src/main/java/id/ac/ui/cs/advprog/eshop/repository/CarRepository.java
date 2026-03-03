package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepository {

    private List<Car> cards = new ArrayList<>();

    public Car create(Car car) {
        if (car.getId() == null) {
            UUID uuid = UUID.randomUUID();
            car.setId(uuid.toString());
        }
        cards.add(car);
        return car;
    }

    public Iterator<Car> findAll() {
        return cards.iterator();
    }

    public Car findById(String id) {
        for (Car car : cards) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public Car update(String id, Car updatedCar) {
        // Reuse findById to reduce complexity and avoid duplicate loop logic
        Car car = findById(id);

        if (car != null) {
            car.setName(updatedCar.getName());
            car.setColor(updatedCar.getColor());
            car.setQuantity(updatedCar.getQuantity());
            return car;
        }

        return null; // Handle the case where the car is not found
    }

    public void delete(String id) {
        cards.removeIf(car -> car.getId().equals(id));
    }
}