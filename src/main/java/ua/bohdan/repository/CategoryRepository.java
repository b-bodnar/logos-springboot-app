package ua.bohdan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bohdan.entity.CategoryEntity;

import java.util.Optional;

// @Repository
public interface CategoryRepository
        extends JpaRepository<CategoryEntity, Long> {

    boolean existsById(Long id);

    boolean existsByNameIgnoreCase(String name);

    Optional<CategoryEntity> findByNameIgnoreCase(String name);
}
