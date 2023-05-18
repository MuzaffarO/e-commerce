package nt.uz.ecommerce.repository;

import nt.uz.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
