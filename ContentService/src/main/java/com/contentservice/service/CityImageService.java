package com.contentservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contentservice.entities.CityImage;
import com.contentservice.entities.dto.CityImageDTO;
import com.contentservice.repositories.CityImageRepository;
import com.contentservice.repositories.CityRepository; // Needed to link images to cities

import jakarta.transaction.Transactional;

@Service
public class CityImageService {

    @Autowired
    private CityImageRepository cityImageRepository;
    
    @Autowired
    private CityRepository cityRepository; // Inject CityRepository to find associated City entities

    // Get all images
    public List<CityImageDTO> getAllCityImages() {
        return cityImageRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get a single image by ID
    public Optional<CityImageDTO> getCityImageById(Integer id) {
        return cityImageRepository.findById(id)
                .map(this::convertToDto);
    }

    // Get images by city ID
    public List<CityImageDTO> getCityImagesByCityId(Integer cityId) {
        return cityImageRepository.findByCityCityId(cityId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Create a new image
    @Transactional
    public Optional<CityImageDTO> createCityImage(CityImageDTO cityImageDTO) {
        // Find the associated City entity
        return cityRepository.findById(cityImageDTO.getCityId())
                .map(city -> {
                    CityImage cityImage = new CityImage();
                    // Copy properties from DTO to entity, excluding ID and cityId (which is handled by setting the City object)
                    BeanUtils.copyProperties(cityImageDTO, cityImage, "imageId", "cityId");
                    cityImage.setCity(city); // Set the associated City object
                    return convertToDto(cityImageRepository.save(cityImage));
                });
    }

    // Update an existing image
    @Transactional
    public Optional<CityImageDTO> updateCityImage(Integer id, CityImageDTO cityImageDTO) {
        return cityImageRepository.findById(id)
                .flatMap(existingImage -> cityRepository.findById(cityImageDTO.getCityId())
                        .map(city -> {
                            BeanUtils.copyProperties(cityImageDTO, existingImage, "imageId", "cityId");
                            existingImage.setCity(city);
                            return convertToDto(cityImageRepository.save(existingImage));
                        }));
    }

    // Delete an image by ID
    @Transactional
    public boolean deleteCityImage(Integer id) {
        if (cityImageRepository.existsById(id)) {
            cityImageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Delete all images associated with a specific city ID
    @Transactional
    public void deleteImagesByCityId(Integer cityId) {
        List<CityImage> imagesToDelete = cityImageRepository.findByCityCityId(cityId);
        cityImageRepository.deleteAll(imagesToDelete);
    }

    // Helper method to convert Entity to DTO
    public CityImageDTO convertToDto(CityImage cityImage) {
        CityImageDTO dto = new CityImageDTO();
        BeanUtils.copyProperties(cityImage, dto);
        // Set cityId in DTO from the associated City entity
        dto.setCityId(cityImage.getCity() != null ? cityImage.getCity().getCityId() : null);
        return dto;
    }
}
