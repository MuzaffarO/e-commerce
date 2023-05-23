package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ResponseDto<ReviewDto> createReview(String comment, short rank, Integer product_id);
    ResponseDto<List<ReviewDto>> getAll(Integer id);
    ResponseDto<ReviewDto> update(ReviewDto reviewDto);
    ResponseDto<ReviewDto> delete(Integer id);
}
