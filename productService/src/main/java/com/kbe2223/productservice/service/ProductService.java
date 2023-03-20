package com.kbe2223.productservice.service;

import com.kbe2223.productservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The ProductService class provides the business logic for interacting with the ProductRepository.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves a price record by ID.
     *
     * @param id The ID of the price record to retrieve.
     * @return The retrieved Product object, or null if no record with the specified ID was found.
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Returns a list of all prices in the database.
     *
     * @return a list of all prices in the database
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    /**
     * Creates a new price record.
     *
     * @param price The Product object representing the new price record.
     * @return The created Product object.
     */
    public Product createProduct(Product price) {
        return productRepository.save(price);
    }

    /**
     * Updates an existing price record.
     *
     * @param id The ID of the price record to update.
     * @param updatedProduct The updated Product object representing the new price information.
     * @return The updated Product object, or null if no record with the specified ID was found.
     */
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setImage(updatedProduct.getImage());
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    /**
     * Deletes an existing price record.
     *
     * @param id The ID of the price record to delete.
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
