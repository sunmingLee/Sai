package com.sai.controller;

import org.springframework.http.HttpStatus;
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

import com.sai.model.dto.plan.CreatePlanRequsetDto;
import com.sai.model.dto.plan.UpdatePlanRequestDto;
import com.sai.model.service.PlanService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/plan")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PlanController {
	
	private final PlanService planService;
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 일정 등록
	@PostMapping
	public ResponseEntity<?> createPlan(@RequestBody CreatePlanRequsetDto createPlanRequsetDto ) throws Exception {
		
		try {
			planService.createPlan(createPlanRequsetDto);
			return ResponseEntity.status(200).body("일정이 등록되었습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	// 일정 삭제
	@DeleteMapping("/{mainPlanId}")
	public ResponseEntity<?> deletePlan(@PathVariable Long mainPlanId) throws Exception {
		try {
			planService.deletePlan(mainPlanId);
			return ResponseEntity.status(200).body("일정이 삭제되었습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 일정 수정
	@PutMapping("/{planId}")
	public ResponseEntity<?> modifyPlan(@RequestBody UpdatePlanRequestDto updatePlanRequestDto) throws Exception {
		try {
			planService.updatePlan(updatePlanRequestDto);
			return ResponseEntity.status(200).body("일정이 수정되었습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 일정 불러오기
	@GetMapping("/{familyId}")
	public ResponseEntity<?> selectAllPlan(@PathVariable String familyId ) throws Exception {
		try {
			return ResponseEntity.status(200).body(planService.getPlanAll(familyId));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	// 일정 상세보기
	@GetMapping("/{familyId}/{mainPlanId}")
	public ResponseEntity<?> selectOnePlan(@PathVariable Long mainPlanId) throws Exception {
		try {
			return ResponseEntity.status(200).body(planService.getPlan(mainPlanId));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	

}
