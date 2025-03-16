package st.severstal.ithub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public Delivery createDelivery(DeliveryRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик не найден"));

        Delivery delivery = Delivery.builder()
                .supplier(supplier)
                .deliveryDate(request.getDeliveryDate())
                .build();

        for (DeliveryRequest.DeliveryItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден"));

            double totalCost = itemReq.getWeight() * itemReq.getPricePerUnit();

            DeliveryItem deliveryItem = DeliveryItem.builder()
                    .product(product)
                    .weight(itemReq.getWeight())
                    .pricePerUnit(itemReq.getPricePerUnit())
                    .totalCost(totalCost)
                    .build();

            delivery.getItems().add(deliveryItem);
            deliveryItem.setDelivery(delivery);
        }
        log.info("Создание поставки для поставщика ID: {}", request.getSupplierId());
        return deliveryRepository.save(delivery);
    }

    @Transactional(readOnly = true)
    public List<DeliveryReportItem> getReport(LocalDate startDate, LocalDate endDate) {
        List<Delivery> deliveries = deliveryRepository.findAllByDeliveryDateBetween(startDate, endDate);

        Map<String, DeliveryReportItem> reportMap = new HashMap<>();

        for (Delivery delivery : deliveries) {
            String supplierName = delivery.getSupplier().getName();

            for (DeliveryItem item : delivery.getItems()) {
                String productName = item.getProduct().getName();
                String typeName = item.getProduct().getType();
                String key = String.format("%s_%s_%s", supplierName, productName, typeName);

                DeliveryReportItem current = reportMap.get(key);
                if (current == null) {
                    current = DeliveryReportItem.builder()
                            .supplierName(supplierName)
                            .productName(productName)
                            .typeName(typeName)
                            .totalQuantity(0.0)
                            .totalCost(0.0)
                            .build();
                }

                double newQuantity = current.getTotalQuantity() + item.getWeight();
                double newCost = current.getTotalCost() + item.getTotalCost();

                current.setTotalQuantity(newQuantity);
                current.setTotalCost(newCost);

                reportMap.put(key, current);
            }
        }
        ArrayList<DeliveryReportItem> result = new ArrayList<>(reportMap.values());
        log.info("Сформирован отчёт: итоговых строк = {}", result.size());
        return result;
    }
}
