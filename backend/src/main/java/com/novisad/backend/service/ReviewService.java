package com.novisad.backend.service;

import com.novisad.backend.dto.ReviewDTO;
import com.novisad.backend.model.*;
import com.novisad.backend.repository.EventRepository;
import com.novisad.backend.repository.LocationRepository;
import com.novisad.backend.repository.ReviewRepository;
import com.novisad.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
                         EventRepository eventRepository, LocationRepository locationRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public Review createAndProcessReview(ReviewDTO reviewDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        Event event = eventRepository.findById(reviewDTO.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + reviewDTO.getEventId()));

        Review newReview = new Review();
        newReview.setUser(user);
        newReview.setEvent(event);
        newReview.setCreatedAt(LocalDateTime.now());
        newReview.setComment(reviewDTO.getComment());

        Rate rating = new Rate();
        rating.setPerformance(reviewDTO.getPerformance());
        rating.setSoundAndLighting(reviewDTO.getSoundAndLighting());
        rating.setVenue(reviewDTO.getVenue());
        rating.setOverallImpression(reviewDTO.getOverallImpression());
        newReview.setRating(rating);

        Review savedReview = reviewRepository.save(newReview);

        updateLocationRating(event.getLocation());

        return savedReview;
    }

    private void updateLocationRating(Location location) {
        List<Review> reviewsForLocation = reviewRepository.findByEvent_Location(location);

        double averageRating = reviewsForLocation.stream()
                .map(review -> review.getRating().getOverallImpression())
                .filter(rating -> rating != null && rating > 0)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        location.setTotalRating(Math.round(averageRating * 100.0) / 100.0);
        locationRepository.save(location);
    }
}