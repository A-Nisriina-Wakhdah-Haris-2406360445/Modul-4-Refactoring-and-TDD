package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductServiceRead {
  List<Product> findAll();
  Product findById(String id);
}
