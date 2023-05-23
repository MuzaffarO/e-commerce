package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.CategoryDto;
import nt.uz.ecommerce.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper implements CommonMapper<CategoryDto, Category>{
}
