package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nt.uz.ecommerce.dto.BrandDto;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    @Id
    @GeneratedValue(generator = "productIdSeq")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "product_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
//    @OneToMany(mappedBy = "product")
//    private List<Image> images;
    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdAt;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Category category;
    private Boolean isAvailable;
}
