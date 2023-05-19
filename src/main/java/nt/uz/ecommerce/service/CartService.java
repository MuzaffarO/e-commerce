package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.CartDto;
import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.model.Users;

import java.util.List;

public interface CartService {
    ResponseDto<CartDto> addProductToCart(Integer productId, Integer cartId);
    Boolean generateCart(Users users);
    ResponseDto<List<ProductDto>> getProductsByUserId(Integer id);

}
