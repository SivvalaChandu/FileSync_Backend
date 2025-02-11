package com.Files.DataSync.service;

import com.Files.DataSync.jwt.JwtUtils;
import com.Files.DataSync.model.User;
import com.Files.DataSync.repository.UserRepository;
import com.Files.DataSync.exception.UserAlreadyExistsException;
import com.Files.DataSync.exception.UserNotFoundException;
import com.Files.DataSync.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    private Map<String, String> generateTokens(String username, String role) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", jwtUtils.issueAccessToken(username, role));
        tokens.put("refresh_token", jwtUtils.issueRefreshToken(username));
        return tokens;
    }

    public Map<String, String> register(User user) {
        // Check if the user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(newUser);

        // Return generated JWT tokens
        return generateTokens(user.getEmail(), "ROLE_USER");
    }

    public Map<String, String> login(User user) {
        // Check if the user exists in the repository
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String role = "ROLE_USER";

            // Return generated JWT tokens
            return generateTokens(user.getEmail(), role);
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}
