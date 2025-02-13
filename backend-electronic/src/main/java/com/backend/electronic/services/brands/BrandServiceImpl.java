package com.backend.electronic.services.brands;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.electronic.models.dto.BrandDto;
import com.backend.electronic.models.dto.CategoryDto;
import com.backend.electronic.models.dto.mapper.BrandDtoMapper;
import com.backend.electronic.models.entities.Brand;
import com.backend.electronic.repositories.BrandRepository;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandDtoMapper brandDtoMapper;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<BrandDto> findAllByCategoryId(Long id) {
        List<Brand> brands = brandRepository.findDistinctByCategoryId(id);
        return brands.stream().map(brandDtoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BrandDto> findById(Long id) {
        return brandRepository.findById(id).map(brandDtoMapper::toDto);
    }

    @Override
    @Transactional
    public BrandDto save(Brand brand) {
        brand.setName(brand.getName());
        brand.setStatus(true);

        return brandDtoMapper.toDto(brandRepository.save(brand));
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
    public void disable(Long id) {

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

}
