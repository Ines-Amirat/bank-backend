package org.bank.controller;

import jakarta.servlet.http.HttpSession;
import org.bank.api.dto.LoginRequest;
import org.bank.api.dto.RegisterRequest;
import org.bank.api.dto.UserDTO;
import org.bank.entities.UserAccount;
import org.bank.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequest req, HttpSession session) {

        UserAccount user = authService.login(req);

        // store user id in session
        session.setAttribute("USER_ID", user.getId());

        UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.email = user.getEmail();

        return dto;
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/me")
    public UserDTO me(HttpSession session) {
        UUID id = (UUID) session.getAttribute("USER_ID");
        if (id == null) return null;

        UserAccount u = authService.getUserById(id);

        UserDTO dto = new UserDTO();
        dto.id = u.getId();
        dto.email = u.getEmail();
        return dto;
    }
}
