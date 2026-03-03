package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFindById() {
        Car car = new Car();
        car.setName("BMW");
        carRepository.create(car);

        Car savedCar = carRepository.findById(car.getId());
        assertNotNull(savedCar);
        assertEquals("BMW", savedCar.getName());
    }

    @Test
    void testUpdateCarRepository() {
        Car car = new Car();
        car.setName("Old Name");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setName("New Name");
        updatedCar.setColor("Blue");
        updatedCar.setQuantity(5);

        carRepository.update(car.getId(), updatedCar);

        Car result = carRepository.findById(car.getId());
        assertEquals("New Name", result.getName());
        assertEquals("Blue", result.getColor());
        assertEquals(5, result.getQuantity());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setName("Car 1");
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setName("Car 2");
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals("Car 1", iterator.next().getName());
        assertEquals("Car 2", iterator.next().getName());
    }

    @Test
    void testUpdateNonExistentCar() {
        Car updatedInfo = new Car();
        updatedInfo.setName("Ghost");
        Car result = carRepository.update("non-existent-id", updatedInfo);
        assertNull(result);
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setName("To be deleted");
        carRepository.create(car);
        String id = car.getId();

        carRepository.delete(id);
        assertNull(carRepository.findById(id));
    }
}