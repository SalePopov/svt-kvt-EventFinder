package com.novisad.backend.controller;

import com.novisad.backend.dto.LoginRequest;
import com.novisad.backend.dto.LoginResponse;
import com.novisad.backend.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.novisad.backend.dto.RegisterRequestDTO;
import com.novisad.backend.model.AccountRequest;
import com.novisad.backend.model.RequestStatus;
import com.novisad.backend.repository.AccountRequestRepository;
import com.novisad.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccountRequestRepository accountRequestRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, AccountRequestRepository accountRequestRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.accountRequestRepository = accountRequestRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register-request")
    public ResponseEntity<String> registerRequest(@RequestBody RegisterRequestDTO requestDTO) {
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        AccountRequest newRequest = new AccountRequest();
        newRequest.setEmail(requestDTO.getEmail());
        newRequest.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        newRequest.setAddress(requestDTO.getAddress());
        newRequest.setStatus(RequestStatus.PENDING);
        newRequest.setCreatedAt(LocalDate.now());

        accountRequestRepository.save(newRequest);

        return new ResponseEntity<>("Registration request sent successfully!", HttpStatus.CREATED);
    }
}