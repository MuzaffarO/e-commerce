package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.CategoryDto;
import nt.uz.ecommerce.dto.ResponseDto;

import java.util.List;

public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto);
    ResponseDto<List<CategoryDto>> getAll();
    ResponseDto<List<CategoryDto>> getById(Integer id);
    ResponseDto<CategoryDto> deleteCategory(Integer id);
}
