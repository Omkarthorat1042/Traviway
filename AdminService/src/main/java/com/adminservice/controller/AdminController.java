package com.adminservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.feign.ContentServiceClient;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	@Autowired
	private ContentServiceClient contentServiceClient;

	@DeleteMapping("/content/places/{id}")
	public ResponseEntity<String> deletePlaceById(@PathVariable("id") Integer id) {
		contentServiceClient.deletePlaceById(id);
		return ResponseEntity.ok("Place with ID " + id + " deleted successfully.");
	}
	
	@DeleteMapping("/content/cities/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Integer id) {
        contentServiceClient.deleteCityById(id);
        return ResponseEntity.ok("City with ID " + id + " deleted successfully.");
    }
    
    @DeleteMapping("/content/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        contentServiceClient.deleteCategoryById(id);
        return ResponseEntity.ok("Category with ID " + id + " deleted successfully.");
    }
	
}
