package management_system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.configuration.properties.JwtProperties;
import management_system.dto.LoginRequestDto;
import management_system.dto.UserResponseDto;
import management_system.exception.ApiErrorResponse;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.UserMapper;
import management_system.model.User;
import management_system.repository.UserRepository;
import management_system.security.CustomUserDetailsService;
import management_system.security.JwtService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller responsible for handling user authentication and cookie management.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "1. Authentication", description = "Endpoints for user login and session management")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    
    // We inject the repository and mapper to fetch and format the user data
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Operation(
            summary = "Log in a user",
            description = "Authenticates the user using email and password. Returns the user's profile data " +
                          "and automatically sets an HttpOnly cookie containing the JWT session token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication. Cookie is set.",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Bad credentials (wrong password or email)",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found in the system",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto request) {
        log.info("Login attempt for user: {}", request.email());

        // 1. Authenticate user credentials (throws exception if invalid)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // 2. Load Spring Security user details and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String jwtToken = jwtService.generateToken(userDetails);
        
        // 3. Fetch the actual User entity from our database to return its data
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found in database"));
                
        // 4. Map the entity to a safe Response DTO
        UserResponseDto userResponseDto = userMapper.toDto(user);
        
        // 5. Build the secure HttpOnly cookie
        long expirationInSeconds = jwtProperties.expiration() / 1000;
        ResponseCookie springCookie = ResponseCookie.from("token", jwtToken)
                .httpOnly(true)       // Prevents JS access
                .secure(false)        // MUST be true in production with HTTPS
                .path("/")            // Available to all API endpoints
                .maxAge(expirationInSeconds)
                .sameSite("Lax")      // CSRF protection
                .build();

        log.info("Login successful for user: {}. Secure cookie attached and user DTO returned.", request.email());

        // 6. Return response with Set-Cookie header AND the user DTO in the body
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(userResponseDto);
    }

    @Operation(
            summary = "Log out the current user",
            description = "Clears the JWT session cookie from the user's browser."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully logged out. Cookie cleared.")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        log.info("Processing logout request");

        // Overwrite the existing cookie with an expired one
        ResponseCookie clearCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // Immediately deletes the cookie
                .build();

        // We return a 204 No Content for a successful logout since there's no body needed
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                .build();
    }
}