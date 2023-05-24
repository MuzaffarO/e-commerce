package nt.uz.ecommerce.rest;


import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.BrandDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandResources {
    private final BrandService brandService;

    @GetMapping
    public ResponseDto<List<BrandDto>> getAllBrand(){
        return brandService.getAllBrands();
    }

    @PostMapping
    public ResponseDto<BrandDto> addNewBrand(@RequestBody String name){
        return brandService.addBrand(name);
    }
}
