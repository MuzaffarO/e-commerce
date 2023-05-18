package nt.uz.ecommerce.service.impl;

import lombok.AllArgsConstructor;
import nt.uz.ecommerce.dto.ErrorDto;
import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.model.Products;
import nt.uz.ecommerce.repository.ProductRepository;
import nt.uz.ecommerce.service.ProductService;
import nt.uz.ecommerce.service.validator.ValidationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import nt.uz.ecommerce.service.mapper.ProductMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static nt.uz.ecommerce.service.additional.AppStatusCodes.*;
import static nt.uz.ecommerce.service.additional.AppStatusMessages.*;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ResponseDto<ProductDto> addNewProduct(ProductDto productDto) {
        List<ErrorDto> errors = ValidationService.validation(productDto);

        if (!errors.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .errors(errors)
                    .data(productDto)
                    .message(VALIDATION_ERROR)
                    .code(-2)
                    .success(false)
                    .build();
        }

        Products product = productMapper.toEntity(productDto);
        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .data(productMapper.toDto(product))
                .success(true)
                .message("Ok")
                .build();
    }

    @Override
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("Product ID is null")
                    .code(-2)
                    .build();
        }

        Optional<Products> productOptional = productRepository.findById(productDto.getId());
        if (productOptional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .message("NOT_FOUND")
                    .code(-1) //NOT_FOUND_ERROR_CODE
                    .data(productDto)
                    .build();
        }

        Products product = productOptional.get();

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null && productDto.getPrice() > 0) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getAmount() != null && productDto.getAmount() > 0) {
            product.setIsAvailable(true);
            product.setAmount(productDto.getAmount());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }

        try {

            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .message("OK")
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message("DATABASE_ERROR" + ": " + e.getMessage())
                    .code(2) //DATABASE_ERROR_CODE
                    .build();
        }    }

    @Override
    public ResponseDto<List<ProductDto>> getAllProducts() {
        return ResponseDto.<List<ProductDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ResponseDto<ProductDto> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(products -> ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(products))
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .message(NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build()
                );    }

    @Override
    public ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params) {
        return null;
    }

    @Override
    public ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort) {
        return null;
    }

    @Override
    public ResponseDto<List<ProductDto>> getExpensiveProducts() {
        return null;
    }
}
