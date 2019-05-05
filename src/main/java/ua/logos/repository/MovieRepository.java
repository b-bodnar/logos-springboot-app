package ua.logos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.logos.entity.MovieEntity;

@Repository
public interface MovieRepository
        extends JpaRepository<MovieEntity, Long> {

    // select count(*) from movies WHERE movie.id = 5;
    boolean existsById(Long id);
}
