package ua.bohdan.service;

import ua.bohdan.domain.MovieDTO;

import java.util.List;

public interface MovieService {

    void saveMovie(MovieDTO movie);

    List<MovieDTO> findAllMovies();

    MovieDTO findMovieById(Long id);

    MovieDTO updateMovie(Long id, MovieDTO movieToUpdate);
}
