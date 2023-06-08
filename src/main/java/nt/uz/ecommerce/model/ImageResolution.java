package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "image_resolution")
@Entity
@Getter
@Setter
public class ImageResolution {
    @Id
    private Integer id;
    private String size;
    @OneToMany(mappedBy = "imageResolution",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Image> productDetails = new ArrayList<>();
}
