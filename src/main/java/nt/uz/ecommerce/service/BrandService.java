package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.BrandDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.model.Brand;

import java.util.List;

public interface BrandService {
    ResponseDto<BrandDto> addBrand(String name);

    ResponseDto<List<BrandDto>> getAllBrands();
}
