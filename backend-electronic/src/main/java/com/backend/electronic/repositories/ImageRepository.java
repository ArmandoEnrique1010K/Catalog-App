package com.backend.electronic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.electronic.models.entities.Image;

// TODO: ¿ELIMINAR ESTE REPOSITORIO?
public interface ImageRepository extends JpaRepository<Image, Long> {

}
