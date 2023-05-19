package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "address")
@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(generator = "addressIdSequence")
    @SequenceGenerator(name = "addressIdSequence", sequenceName = "address_id_seq", allocationSize = 1)
    private Integer id;
    private String country;
    private String city;
    private String apartmentNumber;
    private String postalCode;
//    private String phoneNumber;
}
