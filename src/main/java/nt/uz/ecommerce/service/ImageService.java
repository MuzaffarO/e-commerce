package nt.uz.ecommerce.service;

import nt.uz.ecommerce.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ResponseDto<Integer> fileUpload(Integer userId, String size, MultipartFile file);

    ResponseDto<byte[]> getFileById(Integer fileId, String size) throws IOException;
}
