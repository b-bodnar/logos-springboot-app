package ua.bohdan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.bohdan.entity.MovieEntity;

@Repository
public interface MovieRepository
        extends JpaRepository<MovieEntity, Long> {

    // select count(*) from movies WHERE movie.id = 5;
    boolean existsById(Long id);
}
