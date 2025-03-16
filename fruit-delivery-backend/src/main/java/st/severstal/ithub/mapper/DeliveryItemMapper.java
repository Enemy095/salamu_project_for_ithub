package st.severstal.ithub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import st.severstal.ithub.dto.DeliveryItemDto;
import st.severstal.ithub.entity.DeliveryItem;
import st.severstal.ithub.entity.Product;

@Mapper(componentModel = "spring")
public interface DeliveryItemMapper {

    @Mapping(target = "productId", source = "product.id")
    DeliveryItemDto toDto(DeliveryItem entity);

    @Mapping(target = "product", source = "productId", qualifiedByName = "idToProduct")
    @Mapping(target = "delivery", ignore = true)
    DeliveryItem toEntity(DeliveryItemDto dto);

    @Named("idToProduct")
    default Product idToProduct(Long productId) {
        if (productId == null) {
            return null;
        }
        Product p = new Product();
        p.setId(productId);
        return p;
    }
}
