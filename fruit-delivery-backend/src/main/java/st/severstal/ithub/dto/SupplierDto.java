package st.severstal.ithub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {
    private Long id;
    private String name;
}
