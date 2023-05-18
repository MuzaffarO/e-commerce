package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    @ManyToOne
    private Category category;
    private Boolean isAvailable;
}
