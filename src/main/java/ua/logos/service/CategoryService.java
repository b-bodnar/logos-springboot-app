package ua.logos.service;

import ua.logos.domain.CategoryDTO;
import ua.logos.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    CategoryDTO save(CategoryDTO category);

    List<CategoryDTO> findAll();

    CategoryDTO findByCategoryId(Long id);

    CategoryDTO findByCategoryName(String name);
}
