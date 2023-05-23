package nt.uz.ecommerce.rest;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;


    @PostMapping()
    public ResponseDto<ProductDto> addNewProduct(@RequestBody ProductDto productDto){
        return productService.addNewProduct(productDto);
    }


    @PutMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }


    @GetMapping()
    public ResponseDto<List<ProductDto>> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("by-id")
    public ResponseDto<ProductDto> getProductById(@RequestParam Integer id) {
        return productService.getProductById(id);
    }


//    @GetMapping("search-2")
//    public ResponseDto<Page<ProductDto>> universalSearch(@RequestParam Map<String, String> params) {
//        return productService.universalSearch2(params);
//    }


//    @GetMapping("sort")
//    public ResponseDto<List<ProductDto>> getProducts(@RequestParam List<String> sort) {
//        return productService.getAllProductsWithSort(sort);
//    }

//    @GetMapping("/expensive-by-category")
//    public ResponseDto<List<ProductDto>> getExpensiveProducts(){
//        return productService.getExpensiveProducts();
//    }
}
