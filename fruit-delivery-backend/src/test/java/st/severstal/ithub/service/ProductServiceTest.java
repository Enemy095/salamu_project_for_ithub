package st.severstal.ithub.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import st.severstal.ithub.entity.Product;
import st.severstal.ithub.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_ReturnsList() {
        Product p1 = Product.builder().id(10L).name("ProductA").type("TypeA").build();
        Product p2 = Product.builder().id(11L).name("ProductB").type("TypeB").build();
        List<Product> mockProducts = List.of(p1, p2);

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.getAllProducts();

        assertThat(result).hasSize(2).containsExactly(p1, p2);
        verify(productRepository).findAll();
    }

    @Test
    void getAllProducts_EmptyList() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<Product> result = productService.getAllProducts();

        assertThat(result).isEmpty();
        verify(productRepository).findAll();
    }
}
