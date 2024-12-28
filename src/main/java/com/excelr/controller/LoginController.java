package com.excelr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excelr.model.Category;
import com.excelr.model.Subcategory;
import com.excelr.model.User;
import com.excelr.model.Userdto;
import com.excelr.service.CategoryService;
import com.excelr.service.LoginService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@Validated
public class LoginController {
	
	@Autowired
	private LoginService service;
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return service.saveUser(user);
	}
	
	@PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid Userdto userDTO) {
        return service.login(userDTO);
    }
	
	@GetMapping("/healthcheck")
	public String check() {
		return "The controller is working";
	}
}
