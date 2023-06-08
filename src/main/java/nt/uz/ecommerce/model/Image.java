package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "image")
@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String path;
    private String ext;
    private Integer productId;
    @ManyToOne(fetch = FetchType.LAZY)
    private ImageResolution imageResolution;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private ProductDetails productDetails;
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;
}
