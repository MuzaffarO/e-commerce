package nt.uz.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.AddressDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.model.Address;
import nt.uz.ecommerce.repository.AddressRepository;
import nt.uz.ecommerce.service.AddressService;
import nt.uz.ecommerce.service.additional.ECommerceMessage;
import nt.uz.ecommerce.service.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nt.uz.ecommerce.service.additional.AppStatusCodes.*;
import static nt.uz.ecommerce.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
//    private final UsersService usersService;
    @Override
    public ResponseDto<AddressDto> add(AddressDto addressDto) {
            addressRepository.save(addressMapper.toEntity(addressDto));
            return ResponseDto.<AddressDto>builder()
                    .success(true)
                    .message(ECommerceMessage.ADDRESS_CREATED)
                    .data(addressDto)
                    .build();
    }

    @Override
    public ResponseDto<AddressDto> editAddress(AddressDto addressDto) {
        Optional<Address> address = addressRepository.findById(addressDto.getId())
                .map(address1 -> {
                    address1.setCity(addressDto.getCity());
                    address1.setCountry(addressDto.getCountry());
                    address1.setPhoneNumber(addressDto.getPhoneNumber());
                    address1.setApartmentNumber(addressDto.getApartmentNumber());
                    address1.setPostalCode(addressDto.getPostalCode());
                    return address1;
                });

        if (address.isPresent()) {
            return ResponseDto.<AddressDto>builder()
                    .message(OK)
                    .success(true)
                    .data(addressMapper.toDto(address.get()))
                    .build();
        }
        return ResponseDto.<AddressDto>builder()
                .message("User with ID " + addressDto.getId() + " is not found")
                .code(NOT_FOUND_ERROR_CODE)
                .data(addressDto)
                .build();
    }

    @Override
    public ResponseDto<List<AddressDto>> getAll() {
        try {
            return ResponseDto.<List<AddressDto>>builder()
                    .message(OK)
                    .success(true)
                    .data(addressRepository.findAll().stream()
                            .map(addressMapper::toDto)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<AddressDto>>builder()
                    .code(UNEXPECTED_ERROR_CODE)
                    .message("Unexpected error with database: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<AddressDto> deleteById(int id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            addressRepository.deleteById(id);
            return ResponseDto.<AddressDto>builder()
                    .success(true)
                    .message(ECommerceMessage.ADDRESS_DELETED)
                    .build();
        }
        return ResponseDto.<AddressDto>builder()
                .success(false)
                .code(NOT_FOUND_ERROR_CODE)
                .message(NOT_FOUND)
                .build();
    }

//    @Override
//    public List<Address> getAddressByUserId(int userId) {
//        Optional<ResponseDto<UsersDto>> user = Optional.ofNullable(usersService.getUserById(userId));
//        List<Address> addressesResponse = new ArrayList<>();
//
//        if (user.isPresent()) {
//            for (Address address : user.get().getData().getAddress()) {
//                addressesResponse.add(address);
//            }
//            return addressesResponse;
//        }
//        return null;
//    }

    @Override
    public ResponseDto<AddressDto> getById(int id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return ResponseDto.<AddressDto>builder()
                    .success(true)
                    .message(OK)
                    .data(addressMapper.toDto(address.get()))
                    .build();
        }
        return ResponseDto.<AddressDto>builder()
                .success(false)
                .message(NOT_FOUND)
                .code(NOT_FOUND_ERROR_CODE)
                .build();
    }
}
