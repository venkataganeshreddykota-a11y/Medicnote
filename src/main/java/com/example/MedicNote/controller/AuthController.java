package com.example.MedicNote.controller;

import com.example.MedicNote.entity.Users;
import com.example.MedicNote.entity.Receptionists;
import com.example.MedicNote.repository.UsersRepository;
import com.example.MedicNote.repository.ReceptionistsRepository;
import com.example.MedicNote.security.JwtUtil;
import com.example.MedicNote.dto.LoginRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.example.MedicNote.repository.DoctorsRepository;
import com.example.MedicNote.entity.Doctors;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ReceptionistsRepository receptionistsRepository;
    @Autowired
    private DoctorsRepository doctorsRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        System.out.println("[DEBUG] Attempting login for email: " + email);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            
            // Get user details from database
            Users user = usersRepository.findAll().stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            String role = user.getRole().name().toLowerCase();
            String token = jwtUtil.generateToken(email, role);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            
            // Add user details
            Map<String, Object> userDetailsMap = new HashMap<>();
            userDetailsMap.put("userId", user.getUserId());
            userDetailsMap.put("name", user.getName());
            userDetailsMap.put("email", user.getEmail());
            userDetailsMap.put("role", role);
            
            // Add hospital ID for receptionists
            if ("receptionist".equals(role)) {
                Receptionists receptionist = receptionistsRepository.findAll().stream()
                        .filter(r -> r.getUser().getUserId().equals(user.getUserId()))
                        .findFirst()
                        .orElse(null);
                if (receptionist != null && receptionist.getHospital() != null) {
                    userDetailsMap.put("hospitalId", receptionist.getHospital().getHospitalId());
                }
            }
            
            // Add doctor ID for doctors
            if ("doctor".equals(role)) {
                Doctors doctor = doctorsRepository.findAll().stream()
                        .filter(d -> d.getUser().getUserId().equals(user.getUserId()))
                        .findFirst()
                        .orElse(null);
                if (doctor != null) {
                    userDetailsMap.put("doctorId", doctor.getDoctorId());
                }
            }
            
            response.put("user", userDetailsMap);
            System.out.println("[DEBUG] Login successful for email: " + email + " with role: " + role);
            return response;
        } catch (AuthenticationException e) {
            System.out.println("[DEBUG] Login failed for email: " + email + ". Reason: " + e.getMessage());
            throw new RuntimeException("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> registerRequest) {
        String email = registerRequest.get("email");
        String password = registerRequest.get("password");
        String name = registerRequest.get("name");
        String role = registerRequest.getOrDefault("role", "patient");
        if (usersRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(email))) {
            throw new RuntimeException("User already exists with email: " + email);
        }
        Users user = new Users();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(Users.Role.valueOf(role.toLowerCase()));
        user.setName(name);
        usersRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return response;
    }
} 