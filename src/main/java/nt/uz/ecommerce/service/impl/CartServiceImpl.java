package nt.uz.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.CartDto;
import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.model.Cart;
import nt.uz.ecommerce.model.Products;
import nt.uz.ecommerce.model.Users;
import nt.uz.ecommerce.repository.CartRepository;
import nt.uz.ecommerce.repository.ProductRepository;
import nt.uz.ecommerce.repository.UsersRepository;
import nt.uz.ecommerce.service.CartService;
import nt.uz.ecommerce.service.mapper.CartMapper;
import nt.uz.ecommerce.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static nt.uz.ecommerce.service.additional.AppStatusMessages.*;
import static nt.uz.ecommerce.service.additional.AppStatusCodes.*;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UsersRepository usersRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ResponseDto<CartDto> addProductToCart(Integer productId, Integer cartId) {
        try {

            Optional<Products> productById = productRepository.findById(productId);
            Optional<Cart> cartById = cartRepository.findById(cartId);
            if (productById.isEmpty())
                return ResponseDto.<CartDto>builder()
                        .message("Product " + NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build();

            if (cartById.isEmpty())
                return ResponseDto.<CartDto>builder()
                        .message("Cart " + NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build();

            Cart cart = cartById.get();
            cartById.get().getProducts().add(productById.get());
            cart.setTotalPrice(cartById.get().getTotalPrice() + productById.get().getPrice());
            return ResponseDto.<CartDto>builder()
                    .code(OK_CODE)
                    .success(true)
                    .message(OK)
                    .data(cartMapper.toDto(cartRepository.save(cart)))
                    .build();
        }catch (Exception e){
            return ResponseDto.<CartDto>builder()
                    .message(UNEXPECTED_ERROR + " " + e.getMessage())
                    .code(UNEXPECTED_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public Boolean generateCart(Users users) {
        try {
            Cart cart = new Cart();
            cart.setUser(users);
            cart.setTotalPrice(0);
            cartRepository.save(cart);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public ResponseDto<List<ProductDto>> getProductsByUserId(Integer id) {
        Optional<Users> userById = usersRepository.findById(id);
        if (userById.isEmpty())
            return ResponseDto.<List<ProductDto>>builder()
                .message("User " + NOT_FOUND)
                .code(NOT_FOUND_ERROR_CODE)
                .build();

        Optional<Cart> cart = cartRepository.findByUserId(id);
        return ResponseDto.<List<ProductDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .data(cart.get().getProducts().stream().map(productMapper::toDto).toList())
                .build();

    }


}
