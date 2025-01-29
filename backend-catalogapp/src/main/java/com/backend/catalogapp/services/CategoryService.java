package com.backend.catalogapp.services;

import java.util.List;
import java.util.Optional;

import com.backend.catalogapp.models.dto.CategoryDto;
import com.backend.catalogapp.models.entities.Category;

public interface CategoryService {

    public List<CategoryDto> findAllByStatusTrue();

    public Optional<CategoryDto> findById(Long id);

    public CategoryDto save(Category category);

    public Optional<CategoryDto> update(Category category, Long id);

    public void disable(Long id);

}
