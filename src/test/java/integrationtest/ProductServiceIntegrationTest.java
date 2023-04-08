package integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kbe2223.productservice.ProductServiceApplication;
import com.kbe2223.productservice.entity.Product;
import com.kbe2223.productservice.service.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;



@SpringBootTest(classes = ProductServiceApplication.class)
@AutoConfigureMockMvc
class ProductServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    // productRepository.deleteAll();
    @BeforeEach
    public void setup() {
        //this will also delete the all existing data on product table, so make sure
        // to use a new table or just do not run the following command
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product(null,"description1", "image1", "name1", 10.0);
        Product product2 = new Product(null, "description2", "image2", "name2", 20.0);

        productRepository.save(product1);
        productRepository.save(product2);

        mockMvc.perform(get("/products/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("description1"))
                .andExpect(jsonPath("$[0].image").value("image1"))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].price").value(10.0))
                .andExpect(jsonPath("$[1].description").value("description2"))
                .andExpect(jsonPath("$[1].image").value("image2"))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].price").value(20.0));
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(obj);
    }


    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product(null, "description", "image", "name", 10.0);

        mockMvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.image").value("image"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Create a product
        Product product = new Product(null, "description", "image", "name", 10.0);
        Product savedProduct = productRepository.save(product);

        // Update the product
        savedProduct.setDescription("updated description");
        savedProduct.setImage("updated image");
        savedProduct.setName("updated name");
        savedProduct.setPrice(20.0);

        mockMvc.perform(put("/products/{id}", savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(savedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedProduct.getId()))
                .andExpect(jsonPath("$.description").value("updated description"))
                .andExpect(jsonPath("$.image").value("updated image"))
                .andExpect(jsonPath("$.name").value("updated name"))
                .andExpect(jsonPath("$.price").value(20.0));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Product product = new Product(null, "description", "image", "name", 10.0);
        Product savedProduct = productRepository.save(product);

        mockMvc.perform(delete("/products/" + savedProduct.getId()))
                .andExpect(status().isNoContent());

        Optional<Product> optionalProduct = productRepository.findById(savedProduct.getId());
        assertFalse(optionalProduct.isPresent());
    }


}