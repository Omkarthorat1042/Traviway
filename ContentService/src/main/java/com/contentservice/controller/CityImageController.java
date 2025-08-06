package com.contentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contentservice.entities.dto.CityImageDTO;
import com.contentservice.service.CityImageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/content/city-images")
public class CityImageController {

    @Autowired
    private CityImageService cityImageService;

    @GetMapping
    public ResponseEntity<List<CityImageDTO>> getAllCityImages() {
        List<CityImageDTO> images = cityImageService.getAllCityImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityImageDTO> getCityImageById(@PathVariable Integer id) {
        return cityImageService.getCityImageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-city/{cityId}")
    public ResponseEntity<List<CityImageDTO>> getCityImagesByCityId(@PathVariable Integer cityId) {
        List<CityImageDTO> images = cityImageService.getCityImagesByCityId(cityId);
        if (images.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(images);
    }

    @PostMapping
    public ResponseEntity<CityImageDTO> createCityImage(@Valid @RequestBody CityImageDTO cityImageDTO) {
        return cityImageService.createCityImage(cityImageDTO)
                .map(createdImage -> new ResponseEntity<>(createdImage, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityImageDTO> updateCityImage(@PathVariable Integer id, @Valid @RequestBody CityImageDTO cityImageDTO) {
        return cityImageService.updateCityImage(id, cityImageDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCityImage(@PathVariable Integer id) {
        if (cityImageService.deleteCityImage(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
