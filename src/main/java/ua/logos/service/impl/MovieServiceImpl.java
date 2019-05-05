package ua.logos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.logos.domain.CategoryDTO;
import ua.logos.domain.MovieDTO;
import ua.logos.entity.CategoryEntity;
import ua.logos.entity.MovieEntity;
import ua.logos.repository.MovieRepository;
import ua.logos.service.MovieService;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void saveMovie(MovieDTO movie) {
        movieRepository.save(dtoToEntityMapper(movie));
    }

    @Override
    public List<MovieDTO> findAllMovies() {
        List<MovieEntity> movies =
                movieRepository.findAll();
        return null;
    }

    @Override
    public MovieDTO findMovieById(Long id) {
        boolean exists = movieRepository.existsById(id);

        if (!exists) {
            return null;
        }

        MovieEntity movie = movieRepository.findById(id).get();
        return null;
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieToUpdate) {
        boolean exists = movieRepository.existsById(id);
        if (!exists) {
            return null;
        }

        /*MovieEntity movieFromDB = movieRepository.findById(id).get();
        movieFromDB.setTitle(movieToUpdate.getTitle());
        movieFromDB.setDescription(movieToUpdate.getDescription());
        movieFromDB.setCategory(movieToUpdate.getCategory());
        movieFromDB.setAgeRating(movieToUpdate.getAgeRating());
        movieFromDB.setDuration(movieToUpdate.getDuration());
        movieRepository.save(movieFromDB);*/
        return null;
    }

    private MovieDTO entityToDtoMapper(MovieEntity movieEntity) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movieEntity.getId());
        movieDTO.setTitle(movieEntity.getTitle());
        movieDTO.setDescription(movieEntity.getDescription());
        movieDTO.setAgeRating(movieEntity.getAgeRating());
        movieDTO.setDuration(movieEntity.getDuration());

        CategoryEntity categoryEntity = movieEntity.getCategory();
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setName(categoryEntity.getName());

        movieDTO.setCategory(categoryDTO);
        return movieDTO;
    }

    private MovieEntity dtoToEntityMapper(MovieDTO movieDTO) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movieDTO.getId());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setDescription(movieDTO.getDescription());
        movieEntity.setDuration(movieDTO.getDuration());
        movieEntity.setAgeRating(movieDTO.getAgeRating());

        CategoryDTO categoryDTO = movieDTO.getCategory();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDTO.getId());
        categoryEntity.setName(categoryDTO.getName());

        movieEntity.setCategory(categoryEntity);
        return movieEntity;
    }
}
