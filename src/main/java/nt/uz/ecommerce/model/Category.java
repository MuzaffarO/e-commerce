package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(generator = "category_id_seq")
    @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Products> products;
    private Integer parentId;
}
