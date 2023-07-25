package it.city.tokenvalidation.repository;

import it.city.tokenvalidation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID> {
    boolean existsByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

    Optional<User> findByUsername(String username);

    List<User> findAllByEnabledFalse();

    Optional<User> findByUsernameOrEmailOrPhoneNumberAndPassword(String username, String email, String phoneNumber, String password);

    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

    Optional<User> findByActivationCode(String activationCode);


}
