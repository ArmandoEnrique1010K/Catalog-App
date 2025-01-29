package com.backend.catalogapp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.catalogapp.models.dto.CategoryDto;
import com.backend.catalogapp.models.dto.mapper.CategoryDtoMapper;
import com.backend.catalogapp.models.entities.Category;
import com.backend.catalogapp.repositories.CategoryRepository;
import com.backend.catalogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDtoMapper categoryDtoMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> findAllByStatusTrue() {
        List<Category> categories = categoryRepository.findByStatusTrue();
        return categories.stream().map(
                categoryDtoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CategoryDto> findById(Long id) {
        return Optional.ofNullable(categoryRepository.findById(id).map(categoryDtoMapper::toDto).orElseThrow());
    }

    @Transactional
    @Override
    public CategoryDto save(Category category) {
        category.setName(category.getName());
        category.setStatus(true);
        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    @Override
    public Optional<CategoryDto> update(Category category, Long id) {
        Optional<Category> optional = categoryRepository.findById(id);

        Category categoryOptional = null;

        if (optional.isPresent()) {
            Category categoryDb = optional.orElseThrow();

            categoryDb.setName(category.getName());
            categoryOptional = categoryRepository.save(categoryDb);
        }

        return Optional.ofNullable(categoryOptional).map(categoryDtoMapper::toDto);
    }

    @Transactional
    @Override
    public void disable(Long id) {

        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isPresent()) {
            Category categoryDb = optional.orElseThrow();
            categoryDb.setStatus(false);
            categoryRepository.save(categoryDb);
        }
    }

}
