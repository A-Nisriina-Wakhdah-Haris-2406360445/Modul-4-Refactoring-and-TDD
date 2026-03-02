package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Iterator;

public interface ProductReadOnly {
  Iterator<Product> findAll();
  Product findById(String id);
}
