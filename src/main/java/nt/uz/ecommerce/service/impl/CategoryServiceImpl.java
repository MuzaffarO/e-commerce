package nt.uz.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.CategoryDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.model.Category;
import nt.uz.ecommerce.repository.CategoryRepository;
import nt.uz.ecommerce.service.CategoryService;
import nt.uz.ecommerce.service.additional.AppStatusMessages;
import nt.uz.ecommerce.service.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nt.uz.ecommerce.service.additional.AppStatusCodes.*;
import static nt.uz.ecommerce.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        try {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(
                            categoryRepository.save(
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(OK)
                    .success(true)
                    .build()
                    ;
        } catch (Exception e) {
            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }

    @Override
    public ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto) {
//        if (categoryDto.getId() == null){
//            return ResponseDto.<CategoryDto>builder()
//                    .code(-2)
//                    .message("Category id is null")
//                    .build();
//        }
//        Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getId());
//        if (categoryOptional.isEmpty()){
//            return ResponseDto.<CategoryDto>builder()
//                    .message("NOT_FOUND")
//                    .code(-1)
//                    .data(categoryDto)
//                    .build();
//        }
//
//        Category category = categoryOptional.get();
//        if (categoryDto.getName() != null){
//            category.setName(categoryDto.getName());
//        }
//        if (categoryDto.getParentCategoryId() != null) {
//            category.setParentCategoryId(categoryDto.getParentCategoryId());
//        }
//        categoryRepository.save(category);
//
//        return ResponseDto.<CategoryDto>builder()
//                .message("OK")
//                .success(true)
//                .data(categoryMapper.toDto(category))
//                .build();
        return null;
    }


    @Override
    public ResponseDto<List<CategoryDto>> getAll() {
        List<CategoryDto> categoryList = categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();

        if (categoryList.size() > 1) {
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(AppStatusMessages.OK)
                    .data(categoryList)
                    .success(true)
                    .build();
        }
        return ResponseDto.<List<CategoryDto>>builder()
                .message(AppStatusMessages.NULL_VALUE)
                .success(false)
                .build();
    }

    @Override
    public ResponseDto<List<CategoryDto>> getById(Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(optional.stream().map(categoryMapper::toDto).collect(Collectors.toList()))
                    .build();
        } else {
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<CategoryDto> deleteCategory(Integer id) {
        Optional<Category> categoryById = categoryRepository.findById(id);

        if (categoryById.isPresent()) {
            Category category = categoryById.get();
            categoryRepository.deleteById(id);
            return ResponseDto.<CategoryDto>builder()
                    .message(AppStatusMessages.OK)
                    .code(1)
                    .success(true)
                    .data(categoryMapper.toDto(category))
                    .build();
        }
        return ResponseDto.<CategoryDto>builder()
                .message("Category " + id + " is not available")
                .success(false)
                .code(-1)
                .build();    }
}
