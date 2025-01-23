package com.backend.catalogapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.catalogapp.models.dto.BrandDto;
import com.backend.catalogapp.models.dto.mapper.BrandDtoMapper;
import com.backend.catalogapp.models.entities.Brand;
import com.backend.catalogapp.repositories.BrandRepository;
import com.backend.catalogapp.services.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandDtoMapper brandDtoMapper;

    @Transactional(readOnly = true)
    @Override
    public List<BrandDto> findAll() {

        List<Brand> brands = brandRepository.findByStatusTrue();
        return brandDtoMapper.toDto(brands);

    }
}
