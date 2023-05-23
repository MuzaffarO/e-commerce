package nt.uz.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.ReviewDto;
import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.model.Products;
import nt.uz.ecommerce.model.Review;
import nt.uz.ecommerce.repository.ProductRepository;
import nt.uz.ecommerce.repository.ReviewRepository;
import nt.uz.ecommerce.service.ReviewService;
import nt.uz.ecommerce.service.additional.AppStatusCodes;
import nt.uz.ecommerce.service.additional.AppStatusMessages;
import nt.uz.ecommerce.service.mapper.ReviewMapper;
import nt.uz.ecommerce.service.mapper.UsersMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UsersMapper usersMapper;

    @Override
    public ResponseDto<ReviewDto> createReview(ReviewDto reviewDto, Integer product_id) {
        UsersDto principal = (UsersDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Products> products = productRepository.findById(product_id);
        if(products.isEmpty()){
            return ResponseDto.<ReviewDto>builder()
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .message("Product not found")
                    .build();
        }
        Review review = Review.builder()
                .users(usersMapper.toEntity(principal))
//                .rank(rank)
//                .description(comment)
                .products(products.get())
                .build();
        try {
            return ResponseDto.<ReviewDto>builder()
                    .data(reviewMapper.toDto(
                                    reviewRepository.save(review)
                            )
                    )
                    .message("OK")
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<ReviewDto>builder()
                    .code(AppStatusCodes.DATABASE_ERROR_CODE)
                    .message("Database error")
                    .data(reviewMapper.toDto(review))
                    .build();
        }
    }

    @Override
    public ResponseDto<List<ReviewDto>> getAll(Integer id) {
        List<ReviewDto> reviewList = reviewRepository.findAll().stream().map(reviewMapper::toDto).toList();

        if (reviewList.size() > 0) {
            return ResponseDto.<List<ReviewDto>>builder()
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .message("OK")
                    .data(reviewList)
                    .build();
        }
        return ResponseDto.<List<ReviewDto>>builder()
                .message(AppStatusMessages.NULL_VALUE)
                .success(false)
                .build();
    }

    @Override
    public ResponseDto<ReviewDto> update(ReviewDto reviewDto) {
        return null;
    }

    @Override
    public ResponseDto<ReviewDto> delete(Integer id) {
        UsersDto principal = (UsersDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(reviewMapper.toDto(reviewRepository.findById(id).get()).equals(principal.getId())){
            Optional<Review> commentById = reviewRepository.findById(id);
            if (commentById.isPresent()) {
                Review comment = commentById.get();
                reviewRepository.deleteById(id);
                return ResponseDto.<ReviewDto>builder()
                        .message("Deleted")
                        .code(AppStatusCodes.OK_CODE)
                        .success(true)
                        .data(reviewMapper.toDto(comment))
                        .build();
            }
        }else
            return ResponseDto.<ReviewDto>builder()
                    .message("This comment isn't belong to "+ principal.getFirstName())
                    .success(false)
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .build();

        return ResponseDto.<ReviewDto>builder()
                .message("Comment " +id+ " is not available")
                .success(false)
                .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                .build();
    }
}
