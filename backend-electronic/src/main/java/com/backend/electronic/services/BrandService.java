package com.backend.electronic.services;

import java.util.List;
import java.util.Optional;

import com.backend.electronic.models.dto.BrandDto;
import com.backend.electronic.models.entities.Brand;

public interface BrandService {

    // Listar todas las marcas habilitadas
    public List<BrandDto> findAllByStatusTrue();

    // Listar todas las marcas distintas de todos los productos que corresponden a
    // una misma categoria
    public List<BrandDto> findAllByCategoryId(Long id);

    // Buscar una marca por su id
    public Optional<BrandDto> findById(Long id);

    // Guardar una marca
    public BrandDto save(Brand brand);

    // Editar una marca
    // No se utiliza un BrandRequest porque no se omite ningun campo para
    // validaciones
    public Optional<BrandDto> update(Brand brand, Long id);

    // Inhabilitar una marca
    public void disable(Long id);

    // No se define eliminar una marca, causaria un error en toda la aplicacion
    // eliminando todos los productos que corresponden a la misma marca

    // TODO: ¿COMO SE OBTIENE TODOS LOS PRODUCTOS POR UNA MARCA?
}