package com.example.MedicNote.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("[DEBUG] JwtAuthenticationFilter - Filter called for URI: " + request.getRequestURI());
        
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        System.out.println("[DEBUG] JwtAuthenticationFilter - Request URI: " + request.getRequestURI());
        System.out.println("[DEBUG] JwtAuthenticationFilter - Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("[DEBUG] JwtAuthenticationFilter - Extracted username: " + username);
            } catch (Exception e) {
                System.out.println("[DEBUG] JwtAuthenticationFilter - Error extracting username: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("[DEBUG] JwtAuthenticationFilter - UserDetails: " + userDetails);
            System.out.println("[DEBUG] JwtAuthenticationFilter - UserDetails authorities: " + userDetails.getAuthorities());
            
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("[DEBUG] JwtAuthenticationFilter - Authentication set successfully");
            } else {
                System.out.println("[DEBUG] JwtAuthenticationFilter - Token validation failed");
            }
        } else {
            System.out.println("[DEBUG] JwtAuthenticationFilter - Username is null or authentication already exists");
        }
        
        filterChain.doFilter(request, response);
    }
} 