package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId("60f9e1f0-4c3e-4d5e-8f1e-3f8c5b9e2d1a");
        car.setName("Toyota Supra");
        car.setColor("Red");
        car.setQuantity(1);
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        mockMvc.perform(post("/car/create")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        List<Car> carList = Arrays.asList(car);
        when(carService.findAll()).thenReturn(carList);

        mockMvc.perform(get("/car/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("CarList"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void testEditCarPage() throws Exception {
        when(carService.findById(car.getId())).thenReturn(car);

        mockMvc.perform(get("/car/edit/" + car.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testEditCarPost() throws Exception {
        mockMvc.perform(post("/car/edit")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(carService, times(1)).update(any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(post("/car/delete")
                        .param("carId", car.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(carService, times(1)).delete(car.getId());
    }
}