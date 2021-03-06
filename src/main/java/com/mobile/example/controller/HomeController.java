package com.mobile.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mobile/")
public class HomeController {
	//service A
	@PostMapping(value="/register")
	public String register() {
		return "Register";
	}
	//service B
	@PostMapping(value="/user/detail")
	public String userDetail() {
		return "UserDetail";
	}
}
