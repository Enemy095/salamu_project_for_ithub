package st.severstal.ithub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryItemDto {
    private Long id;
    private Long productId;
    private double weight;
    private double pricePerUnit;
    private double totalCost;
}
