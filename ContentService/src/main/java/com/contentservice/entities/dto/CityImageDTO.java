package com.contentservice.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CityImageDTO {

    private Integer imageId;
    private Integer cityId; // To link with the City entity
    
    @NotBlank(message = "Image URL cannot be empty")
    @Size(max = 500, message = "Image URL must be less than 500 characters")
    private String imageUrl;
    
    // Removed: List<CityImageDTO> cityImages; This list belongs in CityDTO, not CityImageDTO.
}
