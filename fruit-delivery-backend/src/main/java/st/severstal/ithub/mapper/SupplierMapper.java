package st.severstal.ithub.mapper;

import org.mapstruct.Mapper;
import st.severstal.ithub.dto.SupplierDto;
import st.severstal.ithub.entity.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDto toDto(Supplier entity);
    Supplier toEntity(SupplierDto dto);
}
