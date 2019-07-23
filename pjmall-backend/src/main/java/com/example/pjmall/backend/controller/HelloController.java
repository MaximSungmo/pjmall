package com.example.pjmall.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.pjmall.backend.domain.User;
import com.example.pjmall.backend.dto.JSONResult;

@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public ResponseEntity<JSONResult> hello(){
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JSONResult.success("Hello World"));
	}
	
//	@GetMapping("/hello2")
//	public ResponseEntity<JSONResult> hello2(){
//		
//		return ResponseEntity
//				.status(HttpStatus.OK)
//				.body(JSONResult.success("Hello World2"));
//	}
	
	@RequestMapping(value="/hello2",method = RequestMethod.POST)
	public ResponseEntity<JSONResult> hello3(@RequestBody User user,
			HttpServletRequest request, HttpServletResponse response){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JSONResult.success("Hello World3"));
	}
	
}