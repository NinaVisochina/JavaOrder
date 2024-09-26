package org.example.mapper;

import org.example.dto.product.ProductItemDTO;
import org.example.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProductImageMapper.class })
public interface ProductMapper {

    @Mapping(source = "productImages", target = "images")
    ProductItemDTO toDto(ProductEntity entity);

    List<ProductItemDTO> toDto(List<ProductEntity> entities);

    ProductEntity toEntity(ProductItemDTO dto);
}
