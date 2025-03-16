package st.severstal.ithub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import st.severstal.ithub.dto.DeliveryDto;
import st.severstal.ithub.entity.Delivery;
import st.severstal.ithub.entity.DeliveryItem;
import st.severstal.ithub.entity.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "itemsIds", source = "items", qualifiedByName = "itemsToIds")
    DeliveryDto toDto(Delivery entity);

    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "idToSupplier")
    @Mapping(target = "items", ignore = true)
    Delivery toEntity(DeliveryDto dto);

    @Named("itemsToIds")
    default List<Long> itemsToIds(List<DeliveryItem> items) {
        if (items == null) return new ArrayList<>();
        return items.stream()
                .map(DeliveryItem::getId)
                .collect(Collectors.toList());
    }

    @Named("idToSupplier")
    default Supplier idToSupplier(Long supplierId) {
        if (supplierId == null) {
            return null;
        }
        Supplier s = new Supplier();
        s.setId(supplierId);
        return s;
    }
}
