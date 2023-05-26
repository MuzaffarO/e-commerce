package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @GeneratedValue(generator = "deliveryIdSeq")
    @SequenceGenerator(name = "deliveryIdSeq", sequenceName = "delivery_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne
    private Users user;
    @ManyToOne
    private Address address;
//    @OneToOne
//    private OrderDetails orderDetails;
    private Boolean isActive;

}
