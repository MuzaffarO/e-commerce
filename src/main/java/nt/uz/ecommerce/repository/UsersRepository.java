package nt.uz.ecommerce.repository;

import nt.uz.ecommerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findFirstByPhoneNumber(String phoneNumber);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsername(String username);

//    Optional<Users> findFirstByUsername(String username);
}
