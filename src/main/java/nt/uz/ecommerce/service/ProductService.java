package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseDto<ProductDto> addNewProduct(ProductDto productDto);
    ResponseDto<ProductDto> updateProduct(ProductDto productDto);
    ResponseDto<List<ProductDto>> getAllProducts();
    ResponseDto<ProductDto> getProductById(Integer id);

}
