package st.severstal.ithub.mapper;

import org.mapstruct.Mapper;
import st.severstal.ithub.dto.ProductDto;
import st.severstal.ithub.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product entity);
    Product toEntity(ProductDto dto);
}
