package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.AddressDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.model.Address;

import java.util.List;

public interface AddressService {
    ResponseDto<AddressDto> add(AddressDto addressDto);
    ResponseDto<AddressDto> editAddress(AddressDto addressDto);
    ResponseDto<List<AddressDto>> getAll();
    ResponseDto<AddressDto> deleteById(int id);
//    List<Address> getAddressByUserId(int userId);
    ResponseDto<AddressDto> getById(int id);
    ResponseDto<UsersDto> updateAddress(Integer userId, AddressDto addressDto);

}
