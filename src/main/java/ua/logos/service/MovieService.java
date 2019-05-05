package ua.logos.service;

import ua.logos.domain.MovieDTO;
import ua.logos.entity.MovieEntity;

import java.util.List;

public interface MovieService {

    void saveMovie(MovieDTO movie);

    List<MovieDTO> findAllMovies();

    MovieDTO findMovieById(Long id);

    MovieDTO updateMovie(Long id, MovieDTO movieToUpdate);
}
