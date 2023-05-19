package nt.uz.ecommerce.rest;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.CartDto;
import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartResources {
    private final CartService cartService;
    @PostMapping()
    public ResponseDto<CartDto> addProductToCart(@RequestParam Integer productId, @RequestParam Integer cartId) {
        return cartService.addProductToCart(productId, cartId);
    }

    @GetMapping("/{id}")
    public ResponseDto<List<ProductDto>> getProductsByUserId(@PathVariable Integer id){
        return cartService.getProductsByUserId(id);
    }
}
