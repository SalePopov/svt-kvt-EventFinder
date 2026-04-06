package com.novisad.backend.repository;

import com.novisad.backend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l WHERE " +
            "LOWER(l.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(l.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(l.type) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Location> searchLocations(@Param("searchTerm") String searchTerm);
}