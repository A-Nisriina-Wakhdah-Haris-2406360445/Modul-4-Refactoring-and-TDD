package id.ac.ui.cs.advprog.eshop.controller;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.advprog.eshop.service.ProductServiceCRUD;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceRead;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTests {

  @Autowired
  private MockMvc mockMVC;

  @MockBean
  private ProductServiceRead serviceRead;

  @MockBean
  private ProductServiceCRUD serviceCRUD;

  @Test
  void testCreateProductPage() throws Exception {
    mockMVC.perform(get("/product/create"))
        .andExpect(status().isOk())
        .andExpect(view().name("CreateProduct"))
        .andExpect(model().attributeExists("product"));

  }

  @Test
  void testCreateProductPost() throws Exception{
    mockMVC.perform(post("/product/create")
            .param("name", "Testing")
            .param("quantity", "10"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("list"));
  }

  @Test
  void testProductListPage() throws Exception{
    List<Product> productList = new ArrayList<>();
    productList.add(new Product());

    when(serviceRead.findAll()).thenReturn(productList);

    mockMVC.perform(get("/product/list"))
        .andExpect(status().isOk())
        .andExpect(view().name("ProductList"))
        .andExpect(model().attributeExists("products"));
  }

  @Test
  void testEditProductPage() throws Exception{
    Product product = new Product();
    product.setProductId("100");

    when(serviceRead.findById("100")).thenReturn(product);

    mockMVC.perform(get("/product/edit/100"))
        .andExpect(status().isOk())
        .andExpect(view().name("editProduct"))
        .andExpect(model().attributeExists("product"));
  }

  @Test
  void testEditProductPost() throws Exception {
    mockMVC.perform(post("/product/edit")
        .param("id", "1")
        .param("name", "Pringles")
        .param("quantity", "5"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/product/list"));
  }

  @Test
  void testDeleteProduct() throws Exception {
    mockMVC.perform(get("/product/delete/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/product/list"));
  }

}
