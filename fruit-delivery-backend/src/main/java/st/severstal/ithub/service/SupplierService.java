package st.severstal.ithub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import st.severstal.ithub.entity.Supplier;
import st.severstal.ithub.repository.SupplierRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        log.info("Получение поставщиков: итоговых строк = {}", suppliers.size());
        return suppliers;
    }
}
