package com.novisad.backend.controller;

import com.novisad.backend.dto.ChangePasswordDTO;
import com.novisad.backend.dto.UpdateProfileDTO;
import com.novisad.backend.model.User;
import com.novisad.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile(Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPassword(null);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            return new ResponseEntity<>("Incorrect current password.", HttpStatus.BAD_REQUEST);
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            return new ResponseEntity<>("New passwords do not match.", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully.");
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateMyProfile(@RequestBody UpdateProfileDTO profileDTO, Principal principal) {
        User userToUpdate = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userToUpdate.setName(profileDTO.getName());
        userToUpdate.setPhoneNumber(profileDTO.getPhoneNumber());
        userToUpdate.setBirthday(profileDTO.getBirthday());
        userToUpdate.setAddress(profileDTO.getAddress());
        userToUpdate.setCity(profileDTO.getCity());

        User updatedUser = userRepository.save(userToUpdate);

        updatedUser.setPassword(null);
        return ResponseEntity.ok(updatedUser);
    }
}