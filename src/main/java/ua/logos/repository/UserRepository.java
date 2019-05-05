package ua.logos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.logos.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);



}
