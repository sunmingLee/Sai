package com.sai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.service.PlanService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/plan")
@CrossOrigin
@RequiredArgsConstructor
public class PlanController {
	
	private final PlanService planService;
	
	// 일정 등록
	@PostMapping
	public ResponseEntity<T> createPlan(@RequestBody ) {
		
		return null;
	}
	
	
	// 일정 삭제
	@DeleteMapping("/{planId}")
	public ResponseEntity<T> deletePlan(@PathVariable ) {
		
		return null;
	}
	
	// 일정 수정
	@PutMapping("/{planId}")
	public ResponseEntity<T> modifyPlan(@RequestBody ) {
		
		return null;
	}
	
	// 일정 불러오기
	@GetMapping
	public ResponseEntity<T> selectAllPlan() {
		
		return null;
	}
	
	
	// 일정 상세보기
	@GetMapping("/{pladId}")
	public ResponseEntity<T> selectOnePlan(@PathVariable ) {
		
		return null;
	}
	
	

}
