package st.severstal.ithub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import st.severstal.ithub.entity.Product;
import st.severstal.ithub.repository.ProductRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("Получение продуктов: итоговых строк = {}", products.size());
        return products;
    }
}
