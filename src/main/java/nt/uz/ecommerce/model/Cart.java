package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(generator = "cartIdSequence")
    @SequenceGenerator(name = "cartIdSequence", sequenceName = "cart_id_seq", allocationSize = 1)
    private Integer id;
    private Integer totalPrice;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Products> products;
}
