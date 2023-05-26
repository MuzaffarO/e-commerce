package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.DeliveryDto;
import nt.uz.ecommerce.dto.ResponseDto;

import java.util.List;

public interface DeliveryService {
    ResponseDto<DeliveryDto> add(DeliveryDto deliveryDto);
    ResponseDto<DeliveryDto> update(Integer deliveryId, DeliveryDto deliveryDto);
    ResponseDto<DeliveryDto> delete(Integer id);
    ResponseDto<List<DeliveryDto>> getAll();
    ResponseDto<DeliveryDto> getById(Integer id);
}
