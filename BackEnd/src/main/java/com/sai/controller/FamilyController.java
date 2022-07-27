package com.sai.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.entity.Family;
import com.sai.model.repository.FamilyRepository;

@RestController
@RequestMapping("/family")
public class FamilyController {

	@Autowired
	private FamilyRepository familyRepository;

	@PostMapping("/id")
	public ResponseEntity<?> saveFamily(@RequestBody Family family) {
		Family savedFamily = familyRepository.save(family);
		return ResponseEntity.ok(savedFamily);
	}

	@GetMapping()
	public List<Family> all() {

		List<Family> list = familyRepository.findAll();
		return list;
	}

	// ---------------------------------------------------------------------------------------------------------------

	// 가족 코드 생성
	@PostMapping("/create/{userId}")
	public ResponseEntity<?> createFamilyId(@PathVariable String userId) throws Exception {
		return ResponseEntity.ok(null);
	}

	// 가족에서 나가기
	@PatchMapping("/disjoin/{userId}")
	public ResponseEntity<?> disjoinFamily(@PathVariable String userId) throws Exception {
		return ResponseEntity.ok(null);
	}

	// 가족 들어가기 신청
	@PatchMapping("/join/apply")
	public ResponseEntity<?> applyFamily() throws Exception {
		return ResponseEntity.ok(null);
	}

	// 가족 신청 수락/거절
	@PatchMapping("/join/response")
	public ResponseEntity<?> responseApplication() throws Exception {
		return ResponseEntity.ok(null);
	}

	// 가족 신청 결과
	@GetMapping("/result/{userId}")
	public ResponseEntity<?> resultApplication(@PathVariable String userId) throws Exception {
		return ResponseEntity.ok(null);
	}

	// 가족 정보 조회
	@GetMapping("/{familyId}")
	public ResponseEntity<?> searchFamily(@PathVariable String familyId) throws Exception {
		return ResponseEntity.ok(null);
	}

	// 가족 콜사인 조회
	@GetMapping("/list/{familyId}")
	public ResponseEntity<?> searchFamilyList(@PathVariable String familyId) throws Exception {
		return ResponseEntity.ok(null);
	}

	// 가족 정보 + 콜사인 수정
	@PutMapping("/{familyId}")
	public ResponseEntity<?> updateFamily(@PathVariable String familyId) throws Exception {
		return ResponseEntity.ok(null);
	}

}