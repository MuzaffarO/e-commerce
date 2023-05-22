package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.ReviewDto;
import nt.uz.ecommerce.model.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class ReviewMapper implements CommonMapper<ReviewDto, Review>{
}
