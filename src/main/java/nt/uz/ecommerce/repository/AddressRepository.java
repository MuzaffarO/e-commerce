package nt.uz.ecommerce.repository;

import nt.uz.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByCity(String city);
}
