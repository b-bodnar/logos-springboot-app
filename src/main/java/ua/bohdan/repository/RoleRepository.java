package ua.bohdan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bohdan.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleIgnoreCase(String role);
}
