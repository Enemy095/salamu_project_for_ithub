package st.severstal.ithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st.severstal.ithub.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> { }
