package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.model.Products;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductMapper implements CommonMapper<ProductDto, Products> {
}
