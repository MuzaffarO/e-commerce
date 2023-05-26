package nt.uz.ecommerce.rest;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.DeliveryDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("delivery")
@RequiredArgsConstructor
public class DeliveryResources {
    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseDto<List<DeliveryDto>> getAll(){
        return deliveryService.getAll();
    }

    @GetMapping("by-id/{id}")
    public ResponseDto<DeliveryDto> getById(@PathVariable Integer id){
        return deliveryService.getById(id);
    }

    @PostMapping
    public ResponseDto<DeliveryDto> add(@RequestBody DeliveryDto deliveryDto){
        return deliveryService.add(deliveryDto);
    }

    @PatchMapping("{deliveryId}")
    public ResponseDto<DeliveryDto> update(@PathVariable Integer deliveryId, @RequestBody DeliveryDto deliveryDto){
        return deliveryService.update(deliveryId, deliveryDto);
    }

    @DeleteMapping("{id}")
    public ResponseDto<DeliveryDto> delete(@PathVariable Integer id){
        return deliveryService.delete(id);
    }
}
