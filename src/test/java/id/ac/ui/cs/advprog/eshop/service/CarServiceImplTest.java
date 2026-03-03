package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void testUpdateCarService() {
        Car car = new Car();
        car.setId("1");
        car.setName("Tesla");

        carService.update(car);

        verify(carRepository, times(1)).update(eq("1"), any(Car.class));
    }

    @Test
    void testCreate() {
        Car car = new Car();
        carService.create(car);
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(new Car()).iterator());
        List<Car> result = carService.findAll();
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setId("test-id");
        when(carRepository.findById("test-id")).thenReturn(car);

        Car result = carService.findById("test-id");
        assertEquals("test-id", result.getId());
    }

    @Test
    void testDelete() {
        carService.delete("test-id");
        verify(carRepository, times(1)).delete("test-id");
    }
}