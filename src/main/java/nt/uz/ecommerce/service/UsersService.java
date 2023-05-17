package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.UsersDto;

import java.util.List;

public interface UsersService {
    ResponseDto<UsersDto> addUser(UsersDto usersDto);
    ResponseDto<UsersDto> getUserById(Integer id);

    ResponseDto<List<UsersDto>> getAllUsers();

    ResponseDto<UsersDto> updateUser(UsersDto usersDto);

    ResponseDto<UsersDto> deleteUserById(Integer id);

    ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber);
}
