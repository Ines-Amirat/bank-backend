package org.bank.service;

import org.bank.api.dto.LoginRequest;
import org.bank.api.dto.RegisterRequest;
import org.bank.api.dto.UserDTO;
import org.bank.entities.UserAccount;
import org.bank.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO register(RegisterRequest req) {
        if (userRepository.findByEmail(req.email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UserAccount u = new UserAccount();
        u.setEmail(req.email);
        u.setPasswordHash(encoder.encode(req.password));
        u.setFirstName(req.firstName);
        u.setLastName(req.lastName);

        userRepository.save(u);

        UserDTO dto = new UserDTO();
        dto.id = u.getId();
        dto.email = u.getEmail();
        return dto;
    }

    public UserAccount login(LoginRequest req) {
        UserAccount user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public UserAccount getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }



}

