package st.severstal.ithub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DeliveryRequest {
    private Long supplierId;
    private LocalDate deliveryDate;
    private List<DeliveryItemRequest> items;

    @Data
    public static class DeliveryItemRequest {
        private Long productId;
        private Double weight;
        private Double pricePerUnit;
    }
}
