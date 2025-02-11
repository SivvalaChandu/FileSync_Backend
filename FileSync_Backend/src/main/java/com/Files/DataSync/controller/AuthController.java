package com.Files.DataSync.controller;

import com.Files.DataSync.DTO.ApiResponse;
import com.Files.DataSync.exception.InvalidCredentialsException;
import com.Files.DataSync.exception.UserAlreadyExistsException;
import com.Files.DataSync.exception.UserNotFoundException;
import com.Files.DataSync.model.User;
import com.Files.DataSync.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user, HttpServletResponse response) {
        try {
            System.out.println("Registering user: " + user.getEmail());
            System.out.println("Registering user: " + user.getPassword());
            // Register the user and generate tokens
            Map<String, String> tokens = authService.register(user);
            Cookie refreshTokenCookie = createRefreshTokenCookie(tokens.get("refresh_token"));
            response.addCookie(refreshTokenCookie);

            // Return success response
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, tokens.get("access_token"))
                    .body(new ApiResponse(true, "User registered successfully"));
        } catch (UserAlreadyExistsException e) {
            // Handle the case when the user already exists
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user, HttpServletResponse response) {
        try {   
            System.out.println("Logging in user: " + user.getEmail());
            System.out.println("Logging in user: " + user.getPassword());
            // Authenticate and generate tokens
            Map<String, String> tokens = authService.login(user);
            Cookie refreshTokenCookie = createRefreshTokenCookie(tokens.get("refresh_token"));
            response.addCookie(refreshTokenCookie);

            // Return success response
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, tokens.get("access_token"))
                    .body(new ApiResponse(true, "Login successful"));
        } catch (UserNotFoundException | InvalidCredentialsException e) {
            // Handle specific authentication exceptions
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred"));
        }
    }

    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);  // You may want to handle this differently in dev vs prod
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(8 * 60); // 8 minutes
        return refreshTokenCookie;
    }
}
