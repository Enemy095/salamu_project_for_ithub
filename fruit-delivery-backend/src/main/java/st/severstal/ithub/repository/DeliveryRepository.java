package st.severstal.ithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st.severstal.ithub.entity.Delivery;

import java.time.LocalDate;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByDeliveryDateBetween(LocalDate startDate, LocalDate endDate);
}
