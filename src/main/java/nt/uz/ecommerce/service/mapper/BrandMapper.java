package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.BrandDto;
import nt.uz.ecommerce.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BrandMapper implements CommonMapper<BrandDto, Brand>{
}
