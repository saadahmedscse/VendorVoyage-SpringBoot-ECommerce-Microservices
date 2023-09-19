package com.saadahmedev.authservice.service;

import com.saadahmedev.authservice.dto.ApiResponse;
import com.saadahmedev.authservice.dto.CreateAccountRequest;
import com.saadahmedev.authservice.dto.LoginRequest;
import com.saadahmedev.authservice.dto.LoginResponse;
import com.saadahmedev.authservice.entity.Token;
import com.saadahmedev.authservice.entity.User;
import com.saadahmedev.authservice.repository.TokenRepository;
import com.saadahmedev.authservice.repository.UserRepository;
import com.saadahmedev.authservice.util.JwtUtil;
import com.saadahmedev.authservice.util.RequestValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenRepository tokenRepository;

    private String jwtToken = null;

    @Override
    public ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest) {
        ResponseEntity<?> validationResult = requestValidator.isCreateAccountRequestValid(createAccountRequest);
        if (validationResult.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) return validationResult;

        User user = User.builder()
                .name(createAccountRequest.getName())
                .email(createAccountRequest.getEmail())
                .password(passwordEncoder.encode(createAccountRequest.getPassword()))
                .build();

        try {
            userRepository.save(user);
            return new ResponseEntity<>(new ApiResponse(true, "User created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        ResponseEntity<?> validationResult = requestValidator.isLoginRequestvalid(loginRequest);
        if (validationResult.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) return validationResult;

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        jwtToken = jwtUtil.generateToken(userDetails);

        try {
            tokenRepository.save(new Token(jwtToken));
            return new ResponseEntity<>(new LoginResponse(true, "Logged in successfully", jwtToken), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> validateToken(String bearerToken) {
        return null;
    }
}
