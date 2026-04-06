package com.novisad.backend.controller;

import com.novisad.backend.model.AccountRequest;
import com.novisad.backend.model.RequestStatus;
import com.novisad.backend.model.Role;
import com.novisad.backend.model.User;
import com.novisad.backend.repository.AccountRequestRepository;
import com.novisad.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AccountRequestRepository accountRequestRepository;
    private final UserRepository userRepository;

    public AdminController(AccountRequestRepository accountRequestRepository, UserRepository userRepository) {
        this.accountRequestRepository = accountRequestRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/registration-requests/pending")
    public ResponseEntity<List<AccountRequest>> getAllPendingRequests() {
        List<AccountRequest> pendingRequests = accountRequestRepository.findAll()
                .stream()
                .filter(request -> request.getStatus() == RequestStatus.PENDING)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pendingRequests);
    }

    @PostMapping("/registration-requests/approve/{requestId}")
    public ResponseEntity<String> approveRequest(@PathVariable Long requestId) {
        Optional<AccountRequest> requestOpt = accountRequestRepository.findById(requestId);

        if (requestOpt.isEmpty() || requestOpt.get().getStatus() != RequestStatus.PENDING) {
            return new ResponseEntity<>("Request not found or already processed.", HttpStatus.NOT_FOUND);
        }

        AccountRequest request = requestOpt.get();

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setAddress(request.getAddress());
        newUser.setCreatedAt(LocalDate.now());
        newUser.setRole(Role.USER);
        newUser.setName("Default Name");

        userRepository.save(newUser);

        request.setStatus(RequestStatus.ACCEPTED);
        accountRequestRepository.save(request);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/registration-requests/reject/{requestId}")
    public ResponseEntity<String> rejectRequest(@PathVariable Long requestId, @RequestBody(required = false) String reason) {
        Optional<AccountRequest> requestOpt = accountRequestRepository.findById(requestId);

        if (requestOpt.isEmpty() || requestOpt.get().getStatus() != RequestStatus.PENDING) {
            return new ResponseEntity<>("Request not found or already processed.", HttpStatus.NOT_FOUND);
        }

        AccountRequest request = requestOpt.get();
        request.setStatus(RequestStatus.REJECTED);
        if (reason != null && !reason.isEmpty()) {
            request.setRejectionReason(reason);
        }
        accountRequestRepository.save(request);

        return ResponseEntity.ok("Request rejected successfully.");
    }
}