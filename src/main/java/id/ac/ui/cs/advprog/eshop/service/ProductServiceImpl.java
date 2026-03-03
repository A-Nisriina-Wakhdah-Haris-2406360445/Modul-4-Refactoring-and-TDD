package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductCRUD;
import id.ac.ui.cs.advprog.eshop.repository.ProductReadOnly;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductServiceCRUD, ProductServiceRead {

    private final ProductCRUD productCRUD;
    private final ProductReadOnly productRead;

    public ProductServiceImpl(ProductCRUD productCRUD, ProductReadOnly productRead) {
        this.productCRUD = productCRUD;
        this.productRead = productRead;
    }

    @Override
    public Product create(Product product){
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID().toString());
        }
        productCRUD.create(product);
        return product;
    }

    @Override
    public List<Product> findAll(){
        Iterator<Product> productIterator = productRead.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String id){
        return productRead.findById(id);
    }

    @Override
    public void update(Product product){
        productCRUD.update(product);
    }

    @Override
    public void deleteById(String id){
        productCRUD.deleteById(id);
    }

}