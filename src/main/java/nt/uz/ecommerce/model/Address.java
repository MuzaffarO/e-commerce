package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String phoneNumber;
    @ManyToMany
    @JoinTable(
            name = "user_address",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Users> users;

}
