package nt.uz.ecommerce.repository;

import nt.uz.ecommerce.model.Image;
import nt.uz.ecommerce.model.ImageResolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByProductId(Integer productId);
    Optional<List<Image>> findAllByProductId(Integer productId);
}
