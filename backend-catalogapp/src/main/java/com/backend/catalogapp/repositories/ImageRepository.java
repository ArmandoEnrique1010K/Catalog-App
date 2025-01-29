package com.backend.catalogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.catalogapp.models.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
