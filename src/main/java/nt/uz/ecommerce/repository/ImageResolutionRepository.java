package nt.uz.ecommerce.repository;

import nt.uz.ecommerce.model.Image;
import nt.uz.ecommerce.model.ImageResolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageResolutionRepository extends JpaRepository<ImageResolution, Integer> {
    Optional<ImageResolution> findBySize(String size);
}
