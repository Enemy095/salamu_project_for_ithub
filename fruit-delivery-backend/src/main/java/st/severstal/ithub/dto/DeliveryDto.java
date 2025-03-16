package st.severstal.ithub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DeliveryDto {
    private Long id;
    private LocalDate deliveryDate;
    private Long supplierId;
    private List<Long> itemsIds;
}
