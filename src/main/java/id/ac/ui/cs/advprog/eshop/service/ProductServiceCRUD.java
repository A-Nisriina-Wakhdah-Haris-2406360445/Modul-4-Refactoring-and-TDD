package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductServiceCRUD {
    Product create(Product product);
    void update(Product product);
    void deleteById(String id);
}