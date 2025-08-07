package com.adminservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ContentService")
public interface ContentServiceClient {
	
	@DeleteMapping("/api/content/places/{id}")
    void deletePlaceById(@PathVariable("id") Integer id);

    @DeleteMapping("/api/content/cities/{id}")
    void deleteCityById(@PathVariable("id") Integer id);

    @DeleteMapping("/api/content/categories/{id}")
    void deleteCategoryById(@PathVariable("id") Integer id);

    @DeleteMapping("/api/content/local-foods/{id}")
    void deleteLocalFoodById(@PathVariable("id") Integer id);
	
	
	
	
	
}
