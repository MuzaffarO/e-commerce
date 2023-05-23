package nt.uz.ecommerce.rest;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.ReviewDto;
import nt.uz.ecommerce.service.ReviewService;
import nt.uz.ecommerce.service.impl.ReviewServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product/{product_id}/review")
public class ReviewResources {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseDto<List<ReviewDto>> viewAll(@PathVariable Integer product_id){
        return reviewService.getAll(product_id);
    }


}
