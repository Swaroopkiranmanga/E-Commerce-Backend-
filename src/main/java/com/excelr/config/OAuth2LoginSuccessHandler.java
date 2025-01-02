package com.excelr.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.excelr.util.JwtUtil;
import com.excelr.model.Role;
import com.excelr.model.User;
import com.excelr.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        
        if (userOptional.isEmpty()) {
           
            user = new User();
            user.setEmail(email);
            user.setUsername(email);
            user.setPassword(""); 
            user.setRole(Role.ROLE_USER); // Default role
            user = userRepository.save(user);
        } else {
            user = userOptional.get();
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());
        
        // Create response
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("token", token);
        responseMap.put("role", user.getRole().getAuthority());
        responseMap.put("login", "success");
        log.info("Login success : "+email);
        
        // Send JSON response
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    }
}