package com.kbe2223.productservice.service;

import com.kbe2223.productservice.entity.Product;
import com.kbe2223.productservice.publisher.RabbitMQProducer;
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

    private RabbitMQProducer producer;
    public ProductService(RabbitMQProducer producer) {
        this.producer = producer;
    }

    /**
     * Sends selected product to queue for basket insertion
     *
     * @param message The product that will be added to basket.
     */
    public void feedProduct(String message) {
        producer.sendMessage(message);
    }


        /**
         * Retrieves a product record by ID.
         *
         * @param id The ID of the product record to retrieve.
         * @return The retrieved Product object, or null if no record with the specified ID was found.
         */
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Returns a list of all products in the database.
     *
     * @return a list of all products in the database
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    /**
     * Creates a new product record.
     *
     * @param product The Product object representing the new product record.
     * @return The created Product object.
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Updates an existing product record.
     *
     * @param id The ID of the product record to update.
     * @param updatedProduct The updated Product object representing the new product information.
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
     * Deletes an existing product record.
     *
     * @param id The ID of the product record to delete.
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
