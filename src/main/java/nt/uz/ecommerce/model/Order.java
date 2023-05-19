package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import nt.uz.ecommerce.dto.OrderStatus;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "userIdSequence", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
//    @OneToOne
//    private OrderDetails orderDetails;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Products> products;
}
