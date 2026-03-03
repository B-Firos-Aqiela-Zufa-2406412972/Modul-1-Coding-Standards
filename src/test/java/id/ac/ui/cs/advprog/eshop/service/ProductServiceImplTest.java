package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
    }

    @Test
    void testCreate() {
        Product createdProduct = productService.create(product);

        assertEquals(product.getId(), createdProduct.getId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals(product.getId(), result.get(0).getId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById(product.getId())).thenReturn(product);

        Product result = productService.findById(product.getId());

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void testUpdate() {
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDelete() {
        productService.delete(product.getId());

        verify(productRepository, times(1)).delete(product.getId());
    }
}