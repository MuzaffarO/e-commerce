package nt.uz.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.exceptions.FileConvertingException;
import nt.uz.ecommerce.model.ImageResolution;
import nt.uz.ecommerce.repository.ImageRepository;
import nt.uz.ecommerce.repository.ImageResolutionRepository;
import nt.uz.ecommerce.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import nt.uz.ecommerce.model.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageResolutionRepository imageResolutionRepository;
    public String filePath(String folder, String ext) {
        File file = new File("ProductImageFile" + "/" + folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = UUID.randomUUID().toString();

        return file.getPath() + "/" + fileName + ext;
    }


    @Override
    public ResponseDto<Integer> fileUpload(Integer productId, MultipartFile file) {
        List<ImageResolution> resolutions = imageResolutionRepository.findAll();
        Image entity = new Image();
        Image entity2 = new Image();
        Image entity3 = new Image();

        entity.setProductId(productId);
        entity.setImageResolution(resolutions.get(0));

        entity2.setProductId(productId);
        entity2.setImageResolution(resolutions.get(1));

        entity3.setProductId(productId);
        entity3.setImageResolution(resolutions.get(2));

        entity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        entity2.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        entity3.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = null;
        try {
            futures = executorService.invokeAll(
                    Arrays.asList(
                            () -> saveLargeSize(file, productId, entity.getExt()),
                            () -> saveMediumSize(file, productId, entity2.getExt()),
                            () -> saveSmallSize(file, productId, entity3.getExt())
                    )
            );
        } catch (InterruptedException e) {
            throw new FileConvertingException("File converting exception: " + e.getMessage());
        }

        futures.stream()
                .map(result -> {
                    try {
                        return result.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new FileConvertingException(e.getMessage());
                    }
                })
                .forEach(r -> {
                    if (r.substring(17, 22).equalsIgnoreCase("LARGE")) {
                        entity.setPath(r);
                    } else if (r.substring(17, 23).equalsIgnoreCase("MEDIUM")) {
                        entity2.setPath(r);
                    } else if (r.substring(17, 22).equalsIgnoreCase("SMALL")) {
                        entity3.setPath(r);
                    }
                });
        executorService.shutdownNow();


        try {
//            Optional<List<Image>> upImage = imageRepository.findAllByProductId(productId);
            Image savedImage = imageRepository.save(entity);
            imageRepository.save(entity2);
            imageRepository.save(entity3);

            return ResponseDto.<Integer>builder()
                    .data(savedImage.getId())
                    .message("OK")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Integer>builder()
                    .code(2)  //AppStatusCodes.DATABASE_ERROR_CODE
                    .message("Database error" + ": " + e.getMessage()) //AppStatusMessages.DATABASE_ERROR
                    .build();
        }
    }

    @Override
    public ResponseDto<byte[]> getFileById(Integer productId, String size) throws IOException {
        if (productId == null || size == null) {
            return ResponseDto.<byte[]>builder()
                    .message("Null value") //AppStatusMessages.NULL_VALUE
                    .code(-2) //AppStatusCodes.VALIDATION_ERROR_CODE
                    .build();
        }

        AtomicReference<String> imagePath = new AtomicReference<>("");
        Optional<List<Image>> optional = imageRepository.findAllByProductId(productId);

        if (optional.isEmpty()) {
            return ResponseDto.<byte[]>builder()
                    .message("Not found") //AppStatusMessages.NOT_FOUND
                    .code(-1) //AppStatusCodes.NOT_FOUND_ERROR_CODE
                    .build();
        }

        optional.ifPresent(images -> images.stream().map(image -> {
                    try {
                        List<String> list = List.of(
                                image.getPath(), image.getImageResolution().getSize());
                        return list;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(img -> {
                    if (size.equalsIgnoreCase(img.get(1))) {
                        imagePath.set(img.get(0));
                    }
                    if (size.equalsIgnoreCase(img.get(1))) {
                        imagePath.set(img.get(0));
                    }
                    if (size.equalsIgnoreCase(img.get(1))) {
                        imagePath.set(img.get(0));
                    }
                }
        ));

        byte[] file = new FileInputStream(imagePath.get()).readAllBytes();

        return ResponseDto.<byte[]>builder()
                .message("OK") //AppStatusMessages.OK
                .code(0) //AppStatusMessages.OK
                .data(file)
                .success(true)
                .build();
    }


    private String saveLargeSize(MultipartFile file, Integer productId, String ext) {
        String filePathLarge;
        try {
            Files.copy(file.getInputStream(), Path.of(
                    filePathLarge = filePath("large/product" + productId, ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathLarge;
    }

    private String saveMediumSize(MultipartFile file, Integer productId, String ext) {
        String filePathMedium;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImage1 = resizeImage(bufferedImage, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
            ImageIO.write(bufferedImage1, ext.substring(1), new java.io.File(
                    filePathMedium = filePath("medium/product" + productId, ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathMedium;
    }

    private String saveSmallSize(MultipartFile file, Integer productId, String ext) {
        String filePathSmall;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImage1 = resizeImage(bufferedImage, bufferedImage.getWidth() / 2 / 2, bufferedImage.getHeight() / 2 / 2);
            ImageIO.write(bufferedImage1, ext.substring(1), new java.io.File(
                    filePathSmall = filePath("small/product" + productId, ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathSmall;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        return resizedImage;
    }

}
