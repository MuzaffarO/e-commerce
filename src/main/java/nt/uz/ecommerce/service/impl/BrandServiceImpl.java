package nt.uz.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.BrandDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.ReviewDto;
import nt.uz.ecommerce.model.Brand;
import nt.uz.ecommerce.repository.BrandRepository;
import nt.uz.ecommerce.service.BrandService;
import nt.uz.ecommerce.service.additional.AppStatusCodes;
import nt.uz.ecommerce.service.additional.AppStatusMessages;
import nt.uz.ecommerce.service.mapper.BrandMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public ResponseDto<BrandDto> addBrand(String name) {
        Brand brand = new Brand();
        brand.setName(name);
        return ResponseDto.<BrandDto>builder()
                .code(AppStatusCodes.OK_CODE)
                .message(AppStatusMessages.OK)
                .data(brandMapper.toDto(brandRepository.save(brand)))
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<List<BrandDto>> getAllBrands() {
        List<BrandDto> brandList = brandRepository.findAll().stream().map(brandMapper::toDto).toList();

        if (brandList.size() > 0) {
            return ResponseDto.<List<BrandDto>>builder()
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .message("OK")
                    .data(brandList)
                    .build();
        }
        return ResponseDto.<List<BrandDto>>builder()
                .message(AppStatusMessages.NULL_VALUE)
                .success(false)
                .build();
    }
}
