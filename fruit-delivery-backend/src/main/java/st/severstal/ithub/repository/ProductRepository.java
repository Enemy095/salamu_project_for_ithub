package st.severstal.ithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st.severstal.ithub.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> { }
