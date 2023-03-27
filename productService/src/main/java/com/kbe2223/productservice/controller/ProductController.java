package com.kbe2223.productservice.controller;

import com.kbe2223.productservice.entity.Product;
import com.kbe2223.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * The ProductController class defines the REST endpoints for the Product service.
 */
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @CrossOrigin
    @PostMapping(value = "/publish", consumes = {"*/*"})
    public ResponseEntity<String> sendMessage(@RequestBody String message){
        productService.feedProduct(message);
//        System.out.println(message);
        return ResponseEntity.ok("Message sent to RabbitMQ ...");
    }

    /**
     * Retrieves a product record by ID.
     *
     * @param id The ID of the product record to retrieve.
     * @return A ResponseEntity containing the retrieved Product and an HTTP status code.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Returns a list of all products in the database.
     *
     * @return a list of all products in the database, or NOT_FOUND if the database is empty
     */
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Creates a new product record.
     *
     * @param product The Product object representing the new product record.
     * @return A ResponseEntity containing the created Product and an HTTP status code.
     */
    @PostMapping(value = "/",consumes = {"*/*"})
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Updates an existing product record.
     *
     * @param id The ID of the product record to update.
     * @param product The updated Product object representing the new product information.
     * @return A ResponseEntity containing the updated Product and an HTTP status code.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes an existing product record.
     *
     * @param id The ID of the product record to delete.
     * @return A ResponseEntity with an HTTP status code indicating success or failure.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
