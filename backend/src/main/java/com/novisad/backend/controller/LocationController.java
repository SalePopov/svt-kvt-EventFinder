package com.novisad.backend.controller;

import com.novisad.backend.dto.LocationDTO;
import com.novisad.backend.model.Location;
import com.novisad.backend.repository.LocationRepository; // Kreiraćemo ga uskoro
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody LocationDTO locationDTO) {
        Location newLocation = new Location();
        newLocation.setName(locationDTO.getName());
        newLocation.setDescription(locationDTO.getDescription());
        newLocation.setAddress(locationDTO.getAddress());
        newLocation.setType(locationDTO.getType());
        newLocation.setCreatedAt(LocalDate.now());
        newLocation.setTotalRating(0.0);

        Location savedLocation = locationRepository.save(newLocation);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return ResponseEntity.ok(locations);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return locationRepository.findById(id)
                .map(location -> ResponseEntity.ok(location))
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDetails) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Location locationToUpdate = optionalLocation.get();
        locationToUpdate.setName(locationDetails.getName());
        locationToUpdate.setDescription(locationDetails.getDescription());
        locationToUpdate.setAddress(locationDetails.getAddress());
        locationToUpdate.setType(locationDetails.getType());

        Location updatedLocation = locationRepository.save(locationToUpdate);
        return ResponseEntity.ok(updatedLocation);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (!locationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        locationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Location>> searchLocations(@RequestParam(required = false) String query) {
        List<Location> locations;
        if (query == null || query.trim().isEmpty()) {
            locations = locationRepository.findAll();
        } else {
            locations = locationRepository.searchLocations(query);
        }
        return ResponseEntity.ok(locations);
    }
}