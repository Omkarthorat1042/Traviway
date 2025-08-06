package com.contentservice.entities.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CityDTO{

    private Integer cityId;

    @NotBlank(message = "City name cannot be empty")
    @Size(max = 100, message = "City name must be less than 100 characters")
    private String cityName;

    // These are typically not nested directly in microservices DTOs for inter-service calls,
    // but included here for comprehensive content-service API responses if needed.
    // For creation/update, you'd usually pass IDs.
    private List<PlaceDTO> places;
    private List<LocalFoodDTO> localFoods;
    
    @NotBlank(message = "City image cannot be empty")
    @Size(max = 255, message = "City image URL must be less than 255 characters")
    private String cityImage;
}
