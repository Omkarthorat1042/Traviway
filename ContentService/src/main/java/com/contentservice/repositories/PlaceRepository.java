package com.contentservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contentservice.entities.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

	List<Place> findByCityCityId(Integer cityId);
	List<Place> findByCategoryCategoryId(Integer categoryId);
}
