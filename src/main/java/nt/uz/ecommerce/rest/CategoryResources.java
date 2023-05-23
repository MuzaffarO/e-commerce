package nt.uz.ecommerce.rest;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.CategoryDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.service.CategoryService;
import org.springframework.web.bind.annotation.*;
//import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryResources {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseDto<CategoryDto> addNewCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }


    @PatchMapping()
    public ResponseDto<CategoryDto> editCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }


    @GetMapping()
    public ResponseDto<List<CategoryDto>> viewAllCategory(){
        return categoryService.getAll();
    }
    @GetMapping("by-id")
    public ResponseDto<List<CategoryDto>> viewById(@RequestParam Integer id){
        return categoryService.getById(id);
    }

    @DeleteMapping()
    public ResponseDto<CategoryDto> removeCategory(@RequestParam Integer id){
        return categoryService.deleteCategory(id);

    }


}
