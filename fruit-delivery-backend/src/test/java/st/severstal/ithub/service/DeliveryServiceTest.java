package st.severstal.ithub.service;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import st.severstal.ithub.dto.DeliveryReportItem;
import st.severstal.ithub.dto.DeliveryRequest;
import st.severstal.ithub.entity.Delivery;
import st.severstal.ithub.entity.DeliveryItem;
import st.severstal.ithub.entity.Product;
import st.severstal.ithub.entity.Supplier;
import st.severstal.ithub.exception.ResourceNotFoundException;
import st.severstal.ithub.repository.DeliveryRepository;
import st.severstal.ithub.repository.ProductRepository;
import st.severstal.ithub.repository.SupplierRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    @Test
    void createDelivery_success() {
        DeliveryRequest request = getDeliveryRequest();
        Supplier mockSupplier = Supplier.builder().id(100L).name("Поставщик100").build();
        Product mockProduct1 = Product.builder().id(10L).name("Product10").type("TypeA").build();
        Product mockProduct2 = Product.builder().id(20L).name("Product20").type("TypeB").build();
        Delivery savedDelivery = new Delivery();
        savedDelivery.setId(999L);

        when(supplierRepository.findById(100L)).thenReturn(Optional.of(mockSupplier));
        when(productRepository.findById(10L)).thenReturn(Optional.of(mockProduct1));
        when(productRepository.findById(20L)).thenReturn(Optional.of(mockProduct2));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(savedDelivery);

        Delivery result = deliveryService.createDelivery(request);

        assertNotNull(result);
        assertEquals(999L, result.getId());

        verify(supplierRepository).findById(100L);
        verify(productRepository, times(2)).findById(anyLong());
        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    void createDelivery_supplierNotFound() {
        DeliveryRequest request = DeliveryRequest
                .builder()
                .supplierId(999L)
                .build();

        when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> deliveryService.createDelivery(request));

        verify(deliveryRepository, never()).save(any());
    }

    @Test
    void getReport_success() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 31);

        Supplier sup1 = Supplier.builder().id(1L).name("Поставщик1").build();
        Product prodA = Product.builder().id(10L).name("Груша").type("TypeA").build();
        Product prodB = Product.builder().id(20L).name("Яблоко").type("TypeB").build();

        DeliveryItem deliveryItem1 = DeliveryItem.builder()
                .id(101L)
                .product(prodA)
                .weight(50)
                .totalCost(500)
                .build();
        DeliveryItem deliveryItem2 = DeliveryItem.builder()
                .id(102L)
                .product(prodB)
                .weight(10)
                .totalCost(80)
                .build();
        Delivery delivery = Delivery.builder()
                .id(201L)
                .supplier(sup1)
                .deliveryDate(LocalDate.of(2025, 1, 15))
                .items(Arrays.asList(deliveryItem1, deliveryItem2))
                .build();
        deliveryItem1.setDelivery(delivery);
        deliveryItem2.setDelivery(delivery);

        when(deliveryRepository.findAllByDeliveryDateBetween(startDate, endDate))
                .thenReturn(Collections.singletonList(delivery));

        List<DeliveryReportItem> report = deliveryService.getReport(startDate, endDate);

        assertNotNull(report);
        assertEquals(2, report.size());

        DeliveryReportItem r1 = report.get(0);
        DeliveryReportItem r2 = report.get(1);

        Set<String> productNames = new HashSet<>(Arrays.asList(r1.getProductName(), r2.getProductName()));
        assertTrue(productNames.contains("Груша"));
        assertTrue(productNames.contains("Яблоко"));

        verify(deliveryRepository, times(1))
                .findAllByDeliveryDateBetween(startDate, endDate);
    }

    @Test
    void getReport_empty() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 10);

        when(deliveryRepository.findAllByDeliveryDateBetween(start, end))
                .thenReturn(Collections.emptyList());

        List<DeliveryReportItem> report = deliveryService.getReport(start, end);
        assertNotNull(report);
        assertTrue(report.isEmpty());

        verify(deliveryRepository).findAllByDeliveryDateBetween(start, end);
    }

    private static @NotNull DeliveryRequest getDeliveryRequest() {
        DeliveryRequest request = DeliveryRequest
                .builder()
                .supplierId(100L)
                .deliveryDate(LocalDate.of(2025, 3, 20))
                .build();

        DeliveryRequest.DeliveryItemRequest item1 = new DeliveryRequest.DeliveryItemRequest();
        item1.setProductId(10L);
        item1.setWeight(50.0);
        item1.setPricePerUnit(10.0);

        DeliveryRequest.DeliveryItemRequest item2 = new DeliveryRequest.DeliveryItemRequest();
        item2.setProductId(20L);
        item2.setWeight(10.0);
        item2.setPricePerUnit(5.0);

        request.setItems(Arrays.asList(item1, item2));
        return request;
    }
}
