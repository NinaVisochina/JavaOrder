package org.example.mapper;

import org.example.dto.product.ProductImageDto;
import org.example.model.ProductImageEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImageDto toDto(ProductImageEntity entity);

    List<ProductImageDto> toDto(List<ProductImageEntity> entities);

    ProductImageEntity toEntity(ProductImageDto dto);
}

