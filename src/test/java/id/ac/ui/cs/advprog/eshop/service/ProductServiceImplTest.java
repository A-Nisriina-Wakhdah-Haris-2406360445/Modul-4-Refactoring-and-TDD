package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  ProductRepository productRepository;

  @InjectMocks
  ProductServiceImpl productService;

  @Test
  void testCreate() {
    Product product = new Product();
    product.setProductQuantity(12);
    product.setProductName("Nasi goreng");

    Product createdProduct = productService.create(product);
    assertNotNull(createdProduct.getProductId());
  }

  @Test
  void testCreateWithoutId() {
    Product product = new Product();
    product.setProductQuantity(12);
    product.setProductName("Nasi goreng");

    Product createdProduct = productService.create(product);
    assertNotNull(createdProduct.getProductId());
  }

  @Test
  void testCreateWithId() {
    Product product = new Product();
    product.setProductQuantity(12);
    product.setProductName("Nasi goreng");
    product.setProductId("000-009");

    Product createdProduct = productService.create(product);
    assertNotNull(createdProduct.getProductId());
  }

  @Test
  void testFindAll() {
    Product product = new Product();
    product.setProductName("Nasi goreng");
    product.setProductQuantity(10);

    Product product2 = new Product();
    product2.setProductName("Mie ayam");
    product2.setProductQuantity(12);

    Iterator<Product> iterator = Arrays.asList(product, product2).iterator();
    when(productRepository.findAll()).thenReturn(iterator);

    List<Product> result = productService.findAll();
    assertEquals(2, result.size());
  }

  @Test
  void testFindById() {
    Product product = new Product();
    product.setProductName("Mie ayam");
    product.setProductQuantity(12);

    when(productRepository.findById(product.getProductId())).thenReturn(product);

    Product result = productService.findById(product.getProductId());
    assertEquals(product, result);
  }

  @Test
  void testUpdate() {
    Product product = new Product();
    product.setProductName("Ayam bakar");
    product.setProductQuantity(5);

    productService.update(product);

    verify(productRepository, times(1)).update(product);
  }

  @Test
  void testDeleteById() {
    productService.deleteById("1");
    verify(productRepository, times(1)).deleteById("1");
  }
}
