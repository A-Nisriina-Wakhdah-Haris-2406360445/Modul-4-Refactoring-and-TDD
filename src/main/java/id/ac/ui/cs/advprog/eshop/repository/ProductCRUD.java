package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductCRUD {
  Product create(Product product);
  void update(Product product);
  void deleteById(String id);

}
