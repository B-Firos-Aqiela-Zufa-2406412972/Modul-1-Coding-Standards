package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct = productIterator.next();
        assertEquals(product2.getId(), savedProduct.getId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductFound() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setId(product.getId());
        updatedProduct.setName("Sampo Cap Budi");
        updatedProduct.setQuantity(50);
        productRepository.update(updatedProduct);

        Product savedProduct = productRepository.findById(product.getId());
        assertEquals(updatedProduct.getId(), savedProduct.getId());
        assertEquals("Sampo Cap Budi", savedProduct.getName());
        assertEquals(50, savedProduct.getQuantity());
    }

    @Test
    void testEditProductNotFound() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setId("id-ngasal");
        nonExistentProduct.setName("Barang Gaib");

        Product result = productRepository.update(nonExistentProduct);
        assertNull(result);
    }
    @Test
    void testDeleteProductFound() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        productRepository.create(product);

        productRepository.delete(product.getId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductNotFound() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        productRepository.create(product);

        productRepository.delete("id-salah");

        Product savedProduct = productRepository.findById(product.getId());
        assertNotNull(savedProduct);
    }

    @Test
    void testCreateProductWithNullId() {
        Product product = new Product();
        product.setName("Sampo Cap Tanpa ID");
        product.setQuantity(10);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Sampo Cap Tanpa ID", savedProduct.getName());
    }

    @Test
    void testFindByIdIfEmptyAndNotFound() {
        assertNull(productRepository.findById("some-random-id"));

        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        productRepository.create(product1);

        assertNull(productRepository.findById("wrong-id"));
    }

    @Test
    void testUpdateProductNotFound() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        productRepository.create(product1);

        // Try to update using a different product ID that isn't in the list
        Product product2 = new Product();
        product2.setId("id-ngasal");
        product2.setName("Barang Gaib");

        Product result = productRepository.update(product2);

        // Assert that it correctly returns null because it looped through the list and found no match
        assertNull(result);
    }
}