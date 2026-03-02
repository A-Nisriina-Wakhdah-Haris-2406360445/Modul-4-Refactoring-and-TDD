package id.ac.ui.cs.advprog.eshop.controller;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceCRUD;

import id.ac.ui.cs.advprog.eshop.service.ProductServiceRead;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

  private final ProductServiceCRUD serviceCRUD;
  private final ProductServiceRead serviceRead;

  public ProductController(ProductServiceCRUD serviceCRUD, ProductServiceRead serviceRead) {
    this.serviceCRUD = serviceCRUD;
    this.serviceRead = serviceRead;
  }

  @GetMapping("/create")
  public String createProductPage(Model model){
      Product product = new Product();
      model.addAttribute("product", product);
      return "CreateProduct";
  }

  @PostMapping("/create")
  public String createProductPost(@ModelAttribute Product product, Model model){
      serviceCRUD.create(product);
      return "redirect:list";
  }

  @GetMapping("/list")
  public String productListPage(Model model){
      List<Product> allProducts = serviceRead.findAll();
      model.addAttribute("products", allProducts);
      return "ProductList";
  }

  // edit product
  @GetMapping("/edit/{id}")
  public String editProductPage(@PathVariable String id, Model model){
      Product product = serviceRead.findById(id);
      model.addAttribute("product", product);
      return "editProduct";
  }

  @PostMapping("/edit")
  public String editProductPost(@ModelAttribute Product product){
      serviceCRUD.update(product);
      return "redirect:/product/list";
  }

  // delete
  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable String id){
      serviceCRUD.deleteById(id);
      return "redirect:/product/list";
  }
}
