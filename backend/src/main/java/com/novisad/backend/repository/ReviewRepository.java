package com.novisad.backend.repository;

import com.novisad.backend.model.Location;
import com.novisad.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEvent_Location(Location location);

}