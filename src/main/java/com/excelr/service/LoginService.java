package com.excelr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.excelr.model.Category;
import com.excelr.model.Subcategory;
import com.excelr.model.User;
import com.excelr.model.Userdto;
import com.excelr.repository.CategoryRepository;
import com.excelr.repository.UserRepository;
import com.excelr.util.JwtUtil;

import jakarta.validation.Valid;

@Service
public class LoginService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private JwtUtil jwtUtil;

	public User saveUser(User user) {
		return repository.save(user);
	}

	public ResponseEntity<Map<String, String>> login(@Valid Userdto userDTO) {
		String username = userDTO.getUsername();
		String password = userDTO.getPassword();

		Optional<User> user = repository.findByUsername(username);
		if (user.isPresent() && user.get().getPassword().equals(password)) {
			String token = jwtUtil.generateToken(username);

			Map<String, String> response = new HashMap<>();
			response.put("login", "success");
			response.put("token", token);
			response.put("role", user.get().getRole().getAuthority());
			return  ResponseEntity.ok(response);
		} else {
			Map<String, String> failresponse = new HashMap<>();
			failresponse.put("login", "fail");
			return ResponseEntity.status(401).body(failresponse);
		}
	}
	
}
    
