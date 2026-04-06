package com.novisad.backend.controller;

import com.novisad.backend.dto.EventDTO;
import com.novisad.backend.model.Event;
import com.novisad.backend.model.Location;
import com.novisad.backend.repository.EventRepository;
import com.novisad.backend.repository.LocationRepository;
import com.novisad.backend.specification.EventSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventController(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @PostMapping("/locations/{locationId}/events")
    public ResponseEntity<Event> createEvent(@PathVariable Long locationId, @RequestBody EventDTO eventDTO) {
        Optional<Location> locationOpt = locationRepository.findById(locationId);
        if (locationOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Location location = locationOpt.get();

        Event newEvent = new Event();
        newEvent.setName(eventDTO.getName());
        newEvent.setAddress(eventDTO.getAddress());
        newEvent.setType(eventDTO.getType());
        newEvent.setDate(eventDTO.getDate());
        newEvent.setRecurrent(eventDTO.isRecurrent());
        newEvent.setPrice(eventDTO.getPrice());
        newEvent.setLocation(location);

        Event savedEvent = eventRepository.save(newEvent);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventRepository.findAll());
    }

    @GetMapping("/locations/{locationId}/events")
    public ResponseEntity<List<Event>> getEventsByLocation(@PathVariable Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            return ResponseEntity.notFound().build();
        }
        List<Event> events = eventRepository.findByLocationId(locationId);
        return ResponseEntity.ok(events);
    }
    @GetMapping("/events/filter")
    public ResponseEntity<List<Event>> filterEvents(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String locationName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "false") boolean today
    ) {
        if (type == null && locationName == null && address == null && maxPrice == null && !today) {
            return ResponseEntity.ok(eventRepository.findAll());
        }

        List<Event> events = eventRepository.findAll(EventSpecification.filterBy(type, locationName, address, maxPrice, today));
        return ResponseEntity.ok(events);
    }
}