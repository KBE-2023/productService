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
     * Saves all available products in homepage
     */
    public void saveProduct(){
        try {

            Product p1 = new Product(1L,"a","https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg","Mens Casual Slim Fit",15.99);
            Product p2 = new Product(2L,"b","https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg",
                    "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",695.0);
            Product p3 = new Product(3L,"a", "https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg", "Solid Gold Petite Micropave",168.0);
            Product p4 = new Product(4L,"a", "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg", "White Gold Plated Princess", 9.99);
            Product p5 = new Product(5L,"d", "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg", "Pierced Owl Rose Gold Plated Stainless Steel Double", 10.99);
            Product p6 = new Product(6L,"s", "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg", "WD 2TB Elements Portable External Hard Drive - USB 3.0", 64.0);
            Product p7 = new Product(7L,"a", "https://fakestoreapi.com/img/61U7T1koQqL._AC_SX679_.jpg", "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s", 109.0);
            Product p8 = new Product(8L,"a", "https://fakestoreapi.com/img/71kWymZ+c+L._AC_SX679_.jpg", "Silicon Power 256GB SSD 3D NAND A55 SLC Cache Performance Boost SATA III 2.5", 109.0);
            Product p9 = new Product(9L,"s", "https://fakestoreapi.com/img/61mtL65D4cL._AC_SX679_.jpg", "WD 4TB Gaming Drive Works with Playstation 4 Portable External Hard Drive", 114.0);
            Product p10 = new Product(10L,"a", "https://fakestoreapi.com/img/81QpkIctqPL._AC_SX679_.jpg", "Acer SB220Q bi 21.5 inches Full HD (1920 x 1080) IPS Ultra-Thin", 599.0);
            Product p11 = new Product(11L,"a", "https://fakestoreapi.com/img/81Zt42ioCgL._AC_SX679_.jpg",
                    "Samsung 49-Inch CHG90 144Hz Curved Gaming Monitor (LC49HG90DMNXZA) – Super Ultrawide Screen QLED", 999.99);
            Product p12 = new Product(12L,"a", "https://fakestoreapi.com/img/51Y5NI-I5jL._AC_UX679_.jpg", "BIYLACLESEN Women's 3-in-1 Snowboard Jacket Winter Coats", 56.99);
            Product p13 = new Product(13L,"s", "https://fakestoreapi.com/img/81XH0e8fefL._AC_UY879_.jpg", "Lock and Love Women's Removable Hooded Faux Leather Moto Biker Jacket", 29.95);
            Product p14 = new Product(14L,"s", "https://fakestoreapi.com/img/71HblAHs5xL._AC_UY879_-2.jpg", "Rain Jacket Women Windbreaker Striped Climbing Raincoats", 39.99);
            Product p15 = new Product(15L,"s", "https://fakestoreapi.com/img/71z3kpMAYsL._AC_UY879_.jpg", "MBJ Women's Solid Short Sleeve Boat Neck V", 9.85);
            Product p16 = new Product(16L,"a", "https://fakestoreapi.com/img/51eg55uWmdL._AC_UX679_.jpg", "Opna Women's Short Sleeve Moisture", 7.95);
            Product p17 = new Product(17L,"s", "https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg", "DANVOUY Womens T Shirt Casual Cotton Short", 12.99);

            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);
            productRepository.save(p4);
            productRepository.save(p5);
            productRepository.save(p6);
            productRepository.save(p7);
            productRepository.save(p8);
            productRepository.save(p9);
            productRepository.save(p10);
            productRepository.save(p11);
            productRepository.save(p12);
            productRepository.save(p13);
            productRepository.save(p14);
            productRepository.save(p15);
            productRepository.save(p16);
            productRepository.save(p17);
        } catch (Exception e){
            e.printStackTrace();
        }
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
