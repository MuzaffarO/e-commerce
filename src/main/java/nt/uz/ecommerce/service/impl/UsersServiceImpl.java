package nt.uz.ecommerce.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.GetTokenDto;
import nt.uz.ecommerce.dto.LoginDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.model.Users;
import nt.uz.ecommerce.repository.UsersRepository;
import nt.uz.ecommerce.security.JwtService;
import nt.uz.ecommerce.security.UserRoles;
import nt.uz.ecommerce.service.UsersService;
import nt.uz.ecommerce.service.additional.AppStatusCodes;
import nt.uz.ecommerce.service.mapper.UsersMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import nt.uz.ecommerce.security.JwtService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nt.uz.ecommerce.service.additional.AppStatusCodes.*;
import static nt.uz.ecommerce.service.additional.AppStatusMessages.*;


@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Gson gson;

    @Override
    public ResponseDto<UsersDto> addUser(UsersDto usersDto) {
        try {
            Optional<Users> byEmail = usersRepository.findByEmail(usersDto.getEmail());
            Optional<Users> byPhoneNumber = usersRepository.findFirstByPhoneNumber(usersDto.getPhoneNumber());

            if (byEmail.isPresent())
                return ResponseDto.<UsersDto>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message("User with this email " + usersDto.getEmail() + " already exists!")
                        .build();

            if (byPhoneNumber.isPresent())
                return ResponseDto.<UsersDto>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message("User with this username " + usersDto.getPhoneNumber() + " already exists!")
                        .build();
//            usersDto.setPassword(passwordEncoder.encode(usersDto.getPassword()));
            usersDto.setRoles(UserRoles.ROLE_USER);
            Users users = usersMapper.toEntity(usersDto);
            usersRepository.save(users);
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .data(usersMapper.toDto(users))
                    .message(OK)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .data(usersDto)
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<List<UsersDto>> getAllUsers() {
        try {
            return ResponseDto.<List<UsersDto>>builder()
                    .code(AppStatusCodes.OK_CODE)
                    .message(OK)
                    .data(usersRepository.findAll().stream()
                            .map(usersMapper::toDto)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<UsersDto>>builder()
                    .code(1)
                    .message("Unexpected error with database: " + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null) {
            return ResponseDto.<UsersDto>builder()
                    .message("Id is null!")
                    .code(VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }
        try {
            Optional<Users> userOptional = usersRepository.findById(usersDto.getId());
            if (userOptional.isEmpty()) {
                return ResponseDto.<UsersDto>builder()
                        .message("User with ID " + usersDto.getId() + " is not found")
                        .code(NOT_FOUND_ERROR_CODE)
                        .data(usersDto)
                        .build();
            }
            if (usersDto.getEmail() != null) {
                Optional<Users> byEmail = usersRepository.findByEmail(usersDto.getEmail());
                if (byEmail.isPresent() && !userOptional.get().getEmail().equals(usersDto.getEmail()))
                    return ResponseDto.<UsersDto>builder()
                            .code(VALIDATION_ERROR_CODE)
                            .message("User with this email " + usersDto.getEmail() + " already exists!")
                            .build();
            }
            if (usersDto.getPhoneNumber() != null) {
                Optional<Users> byPhoneNumber = usersRepository.findFirstByPhoneNumber(usersDto.getPhoneNumber());
                if (byPhoneNumber.isPresent() && !userOptional.get().getPhoneNumber().equals(usersDto.getPhoneNumber()))
                    return ResponseDto.<UsersDto>builder()
                            .code(VALIDATION_ERROR_CODE)
                            .message("User with this phone number " + usersDto.getPhoneNumber() + " already exists!")
                            .build();
            }

            Users updatedUser = editUserInfo(userOptional.get(), usersDto);
            updatedUser.setUpdatedAt(LocalDateTime.now());
            usersRepository.save(updatedUser);

            return ResponseDto.<UsersDto>builder()
                    .data(usersMapper.toDto(updatedUser))
                    .success(true)
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .code(1)
                    .message("Unexpected error with database: " + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<UsersDto> getUserById(Integer id) {
        try {
            Optional<Users> byId = usersRepository.findById(id);
            if (byId.isEmpty()) {
                return ResponseDto.<UsersDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .message(OK)
                    .data(usersMapper.toDto(byId.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> deleteUserById(Integer id) {
        try {
            Optional<Users> byId = usersRepository.findById(id);
            if (byId.isPresent()) {
                usersRepository.deleteById(id);
                return ResponseDto.<UsersDto>builder()
                        .success(true)
                        .message(OK)
                        .data(usersMapper.toDto(byId.get()))
                        .build();
            }
            return ResponseDto.<UsersDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumber(phoneNumber)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .data(usersMapper.toDto(u))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .message("User with phone number " + phoneNumber + " is not found")
                        .code(-1)
                        .build());
    }

    public Users editUserInfo(Users user, UsersDto usersDto) {
        user.setEmail(Optional.ofNullable(usersDto.getEmail()).orElse(user.getEmail()));
        user.setGender(Optional.ofNullable(usersDto.getGender()).orElse(user.getGender()));
        user.setFirstName(Optional.ofNullable(usersDto.getFirstName()).orElse(user.getFirstName()));
        user.setLastName(Optional.ofNullable(usersDto.getLastName()).orElse(user.getLastName()));
        user.setMiddleName(Optional.ofNullable(usersDto.getMiddleName()).orElse(user.getMiddleName()));
        user.setBirthDate(Optional.ofNullable(usersDto.getBirthDate()).orElse(user.getBirthDate()));
        user.setPhoneNumber(Optional.ofNullable(usersDto.getPhoneNumber()).orElse(user.getPhoneNumber()));

        return user;
    }

    @Override
    public UsersDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findByFirstName(username);
        if(users.isEmpty()) throw new UsernameNotFoundException("user is not found");

        return usersMapper.toDto(users.get());
    }

    @Override
    public ResponseDto<String> getToken(GetTokenDto getTokenDto) {
        Optional<Users> user = usersRepository.findByEmail(getTokenDto.getEmail());
        
        if (user.isEmpty()) {
            ResponseDto.<String>builder()
                    .code(1)
                    .message("Email or password is incorrect")
                    .build();
        }
        Users users = user.get();
        if(!passwordEncoder.matches(getTokenDto.getPassword(), user.get().getPassword())){
            return ResponseDto.<String>builder()
                    .message("Password is not correct")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }
        return ResponseDto.<String>builder()
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .data(jwtService.generateToken(gson.toJson(users), List.of(String.valueOf(user.get().getRoles()))))
                .build();

    }
//    public ResponseDto<String> login(LoginDto loginDto) {
//        UsersDto users = loadUserByUsername(loginDto.getUsername());
//        if(!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())){
//            return ResponseDto.<String>builder()
//                    .message("Password is not correct")
//                    .code(VALIDATION_ERROR_CODE)
//                    .build();
//        }
//
//        return ResponseDto.<String>builder()
//                .code(OK_CODE)
//                .message(OK)
//                .data(jwtService.generateToken(users))
//                .success(true)
//                .build();
//    }
}
