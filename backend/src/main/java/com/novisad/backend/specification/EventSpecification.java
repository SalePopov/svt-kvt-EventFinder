package com.novisad.backend.specification;

import com.novisad.backend.model.Event;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification {

    public static Specification<Event> filterBy(String type, String locationName, String address, Double maxPrice, Boolean isToday) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null && !type.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + type.toLowerCase() + "%"));
            }
            if (locationName != null && !locationName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("location").get("name")), "%" + locationName.toLowerCase() + "%"));
            }
            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (isToday != null && isToday) {
                predicates.add(criteriaBuilder.equal(root.get("date"), LocalDate.now()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}