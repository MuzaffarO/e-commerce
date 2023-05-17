package nt.uz.ecommerce.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;
    @PostMapping("sign-up")
    public ResponseDto<UsersDto> addUser(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }
    @PatchMapping("edit-user")
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }
    @GetMapping("users-list")
    public ResponseDto<List<UsersDto>> getAllUsers(){
        return usersService.getAllUsers();
    }
    @DeleteMapping("delete-by-id")
    public ResponseDto<UsersDto> deleteUserById(@RequestParam Integer id){
        return usersService.deleteUserById(id);
    }

    @GetMapping("user-by-id")
    public ResponseDto<UsersDto> getUserById(@RequestParam Integer id){
        return usersService.getUserById(id);
    }
    @GetMapping("by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }


}

