package nt.uz.ecommerce.rest;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageResources {

    private final ImageService imageService;

    @PostMapping()
    public ResponseDto<Integer> uploadFile(@RequestParam Integer productId, @RequestPart("file") MultipartFile file){
        return imageService.fileUpload(productId, file);
    }

    @GetMapping()
    public ResponseDto<byte[]> getFileById(@RequestParam Integer productId, @RequestParam String size) throws IOException {
        return imageService.getFileById(productId, size);
    }

}
