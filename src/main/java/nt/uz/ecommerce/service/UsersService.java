package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.GetTokenDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.UsersDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UsersService {
    ResponseDto<UsersDto> addUser(UsersDto usersDto);
    ResponseDto<UsersDto> getUserById(Integer id);

    ResponseDto<List<UsersDto>> getAllUsers();

    ResponseDto<UsersDto> updateUser(UsersDto usersDto);

    ResponseDto<UsersDto> deleteUserById(Integer id);

    ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber);

    UsersDto loadUserByUsername(String username) throws UsernameNotFoundException;

    ResponseDto<String> getToken(GetTokenDto getTokenDto);
}
