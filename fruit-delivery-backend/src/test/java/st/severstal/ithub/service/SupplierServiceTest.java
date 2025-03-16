package st.severstal.ithub.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import st.severstal.ithub.entity.Supplier;
import st.severstal.ithub.repository.SupplierRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @Test
    void getAllSuppliers_ReturnsList() {
        Supplier s1 = Supplier.builder().id(1L).name("Supplier1").build();
        Supplier s2 = Supplier.builder().id(2L).name("Supplier2").build();
        List<Supplier> mockList = List.of(s1, s2);

        when(supplierRepository.findAll()).thenReturn(mockList);

        List<Supplier> result = supplierService.getAllSuppliers();

        assertThat(result).hasSize(2).containsExactly(s1, s2);
        verify(supplierRepository).findAll();
    }

    @Test
    void getAllSuppliers_EmptyList() {
        when(supplierRepository.findAll()).thenReturn(List.of());

        List<Supplier> result = supplierService.getAllSuppliers();

        assertThat(result).isEmpty();
        verify(supplierRepository).findAll();
    }
}
