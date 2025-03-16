package st.severstal.ithub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryReportItem {
    private String supplierName;
    private String productName;
    private String typeName;
    private Double totalQuantity;
    private Double totalCost;
}
