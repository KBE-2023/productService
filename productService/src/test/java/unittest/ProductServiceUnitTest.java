package unittest;

import com.kbe2223.productservice.entity.Product;
import com.kbe2223.productservice.service.ProductRepository;
import com.kbe2223.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductServiceUnitTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetProductById() {
        Long id = 1L;
        Product product = new Product(id, "test", "test-image", "test-name", 10.0);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getImage(), result.getImage());
        assertEquals(product.getPrice(), result.getPrice());

        verify(productRepository, times(1)).findById(id);
    }


    @Test
    public void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "test1", "test-image1", "test-name1", 10.0),
                new Product(2L, "test2", "test-image2", "test-name2", 20.0));

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(products.size(), result.size());

        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i).getId(), result.get(i).getId());
            assertEquals(products.get(i).getName(), result.get(i).getName());
            assertEquals(products.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(products.get(i).getImage(), result.get(i).getImage());
            assertEquals(products.get(i).getPrice(), result.get(i).getPrice());
        }

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(null, "test", "test-image", "test-name", 10.0);

        when(productRepository.save(product)).thenReturn(new Product(1L, product.getDescription(),
                product.getImage(), product.getName(), product.getPrice()));

        Product result = productService.createProduct(product);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getImage(), result.getImage());
        assertEquals(product.getPrice(), result.getPrice());

        verify(productRepository, times(1)).save(product);
    }


    @Test
    public void testUpdateProduct() {
        Long id = 1L;
        Product product = new Product(id, "test", "test-image", "test-name", 10.0);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = new Product(id, "updated-test", "updated-test-image", "updated-test-name", 20.0);

        Product result = productService.updateProduct(id, updatedProduct);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getDescription(), result.getDescription());
        assertEquals(updatedProduct.getImage(), result.getImage());
        assertEquals(updatedProduct.getPrice(), result.getPrice());

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(product);
    }


}