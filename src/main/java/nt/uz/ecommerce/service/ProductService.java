package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.dto.ResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseDto<ProductDto> addNewProduct(ProductDto productDto);
    ResponseDto<ProductDto> updateProduct(ProductDto productDto);
    ResponseDto<List<ProductDto>> getAllProducts();
    ResponseDto<ProductDto> getProductById(Integer id);
    ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params);
    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);
    ResponseDto<List<ProductDto>> getExpensiveProducts();

//    ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size);
//    ResponseDto<Stream<ProductDto>> getProductsForSales(List<Integer> salesList);
//    ResponseDto<Stream<ProductDto>> getAllProductsWithLessAmount();
}
