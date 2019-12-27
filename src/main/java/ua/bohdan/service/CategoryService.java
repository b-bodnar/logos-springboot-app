package ua.bohdan.service;

import ua.bohdan.domain.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO save(CategoryDTO category);

    List<CategoryDTO> findAll();

    CategoryDTO findByCategoryId(Long id);

    CategoryDTO findByCategoryName(String name);
}
