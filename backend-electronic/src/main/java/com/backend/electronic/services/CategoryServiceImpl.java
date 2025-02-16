package com.backend.electronic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.electronic.models.dto.CategoryDto;
import com.backend.electronic.models.dto.mapper.CategoryDtoMapper;
import com.backend.electronic.models.entities.Category;
import com.backend.electronic.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDtoMapper categoryDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAllByStatusTrue() {
        List<Category> categories = categoryRepository.findByStatusTrue();
        return categories.stream().map(
                categoryDtoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id).map(categoryDtoMapper::toDto);
    }

    @Override
    @Transactional
    public CategoryDto save(Category category) {
        category.setName(category.getName());
        category.setStatus(true);
        return categoryDtoMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void disable(Long id) {

        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isPresent()) {
            Category categoryDb = optional.orElseThrow();
            categoryDb.setStatus(false);
            categoryRepository.save(categoryDb);
        }
    }

}
