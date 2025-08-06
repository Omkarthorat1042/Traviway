package com.contentservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contentservice.entities.CityImage;

@Repository
public interface CityImageRepository extends JpaRepository<CityImage, Integer> {

    // Custom method to find all images associated with a specific city ID
    List<CityImage> findByCityCityId(Integer cityId);
}
