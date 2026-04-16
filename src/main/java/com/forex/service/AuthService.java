package com.forex.service;

import com.forex.config.JwtService;
import com.forex.dto.AuthResponse;
import com.forex.dto.LoginRequest;
import com.forex.dto.RegisterRequest;
import com.forex.model.User;
import com.forex.model.Wallet;
import com.forex.repository.UserRepository;
import com.forex.repository.WalletRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthService(UserRepository userRepository, WalletRepository walletRepository, 
                       PasswordEncoder passwordEncoder, JwtService jwtService, 
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? request.getRole() : User.Role.TRADER);
        
        user = userRepository.save(user);
        
        Wallet wallet = new Wallet();
        wallet.setTraderId(user.getId());
        wallet.setBalance(0.0);
        wallet.setCurrency("USD");
        walletRepository.save(wallet);
        
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
    
    public boolean verifyPassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return passwordEncoder.matches(password, user.getPassword());
    }
}