package nt.uz.ecommerce.rest;


import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.AddressDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
@RequiredArgsConstructor
public class AddressResources {

    private final AddressService addressService;

    @PostMapping()
    public ResponseDto<AddressDto> add(@RequestBody AddressDto addressDto) {
        addressService.add(addressDto);
        return addressService.add(addressDto);
    }

    @PatchMapping()
    public ResponseDto<AddressDto> edit(@RequestBody AddressDto addressDto) {
        return addressService.editAddress(addressDto);
    }

    @GetMapping()
    public ResponseDto<List<AddressDto>> getAll() {
        return addressService.getAll();
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseDto<AddressDto> deleteById(@PathVariable Integer id) {
        return addressService.deleteById(id);
    }

//    @GetMapping("getAddressByUserId/{userId}")
//    public ResponseEntity<?> getAddressByUserId(@PathVariable int userId) {
//        return ResponseEntity.ok(addressService.getAddressByUserId(userId));
//    }

    @GetMapping("getById/{id}")
    public ResponseDto<AddressDto> getById(@PathVariable Integer id) {
        return addressService.getById(id);
    }

    @PostMapping("add-user-address/{userId}")
    public ResponseDto<UsersDto> addUserAddress(@PathVariable Integer userId, @RequestBody AddressDto addressDto) {
        return addressService.updateAddress(userId, addressDto);

    }
}
