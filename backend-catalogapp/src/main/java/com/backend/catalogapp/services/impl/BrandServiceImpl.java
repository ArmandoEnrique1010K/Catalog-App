package com.backend.catalogapp.services.impl;

import java.util.List;
import java.util.Optional;

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
    public List<BrandDto> findAllByStatusTrue() {

        // 1° forma (versiones anteriores de java 16)
        // List<Brand> brands = (List<Brand>) brandRepository.findByStatusTrue();
        // return brands.stream().map(u -> brandDtoMapper.toDto(u))
        // .collect(Collectors.toList());

        // 2° forma
        List<Brand> brands = brandRepository.findByStatusTrue();
        return brands.stream().map(brandDtoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BrandDto save(Brand brand) {
        brand.setName(brand.getName());
        brand.setStatus(true);

        return brandDtoMapper.toDto(brandRepository.save(brand));

        // return (brandDtoMapper::toDto)(brandRepository.save(brand));
    }

    @Override
    @Transactional
    public Optional<BrandDto> update(Brand brand, Long id) {
        Optional<Brand> optional = brandRepository.findById(id);

        Brand brandOptional = null;

        if (optional.isPresent()) {
            Brand brandDb = optional.orElseThrow();

            brandDb.setName(brand.getName());
            brandOptional = brandRepository.save(brandDb);
        }

        return Optional.ofNullable(brandOptional).map(brandDtoMapper::toDto);

    }

    @Override
    @Transactional
    public void remove(Long id) {

        Optional<Brand> optional = brandRepository.findById(id);

        if (optional.isPresent()) {
            Brand brandDb = optional.orElseThrow();
            brandDb.setStatus(false);
            brandRepository.save(brandDb);
        }

        // NO recomiendo borrar una marca desde la BD, porque se perdería la integridad
        // de los datos
        // brandRepository.deleteById(id);
    }

    @Override
    public Optional<BrandDto> findById(Long id) {
        return Optional.ofNullable(brandRepository.findById(id).map(brandDtoMapper::toDto).orElseThrow());

    }

}
