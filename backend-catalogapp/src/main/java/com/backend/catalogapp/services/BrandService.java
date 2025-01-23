package com.backend.catalogapp.services;

import java.util.List;

import com.backend.catalogapp.models.dto.BrandDto;

public interface BrandService {

    // Listar todas las marcas
    public List<BrandDto> findAll();

    // TODO: DEFINIR LOS SIGUIENTES METODOS
    // Guardar una marca
    // Editar una marca
    // Habilitar/inhabilitar una marca

    // No se define eliminar una marca, causaria un error en toda la aplicacion
    // eliminando todos los productos que corresponden a la misma marca

    // TODO: ¿COMO SE OBTIENE TODOS LOS PRODUCTOS POR UNA MARCA?
}