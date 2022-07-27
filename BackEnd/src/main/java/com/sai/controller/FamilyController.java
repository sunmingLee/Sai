package com.sai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.entity.Family;
import com.sai.model.entity.User;
import com.sai.model.repository.FamilyRepository;
import com.sai.model.service.FamilyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/family")
@CrossOrigin(origins = "*")
public class FamilyController {

	@Autowired
	private FamilyService familyService;

//	@Autowired
//	private FamilyRepository familyRepository;

//	@PostMapping("/id")
//	public ResponseEntity<?> saveFamily(@RequestBody Family family) {
//		Family savedFamily = familyRepository.save(family);
//		return ResponseEntity.ok(savedFamily);
//	}
//
//	@PostMapping("/{id}")
//	public ResponseEntity<?> saveFamily(@PathVariable String id) {
//		Family family = Family.builder().familyId(id).build();
//		Family savedFamily = familyRepository.save(family);
//		return ResponseEntity.ok(savedFamily);
//	}
//
//	@GetMapping()
//	public List<Family> all() {
//		List<Family> list = familyRepository.findAll();
//		return list;
//	}

	// ---------------------------------------------------------------------------------------------------------------

	@ApiOperation(value = "createFamilyId : 가족 코드 생성")
	@PostMapping("/create/{userId}")
	public ResponseEntity<?> createFamilyId(@PathVariable String userId) throws Exception {

		try {
			User user = familyService.createFamilyId(userId);

			if (user != null) {
				return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@ApiOperation(value = "disjoinFamily : 가족에서 나가기")
	@PatchMapping("/disjoin/{userId}")
	public ResponseEntity<?> disjoinFamily(@PathVariable String userId) throws Exception {

		try {
			User user = familyService.disjoinFamily(userId);

			if (user != null) {
				return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@ApiOperation(value = "applyFamily : 가족 들어가기 신청")
	@PatchMapping("/join/apply")
	public ResponseEntity<?> applyFamily() throws Exception {
		return ResponseEntity.ok(null);
	}

	@ApiOperation(value = "responseApplication : 가족 신청 수락/거절")
	@PatchMapping("/join/response")
	public ResponseEntity<?> responseApplication() throws Exception {
		return ResponseEntity.ok(null);
	}

	@ApiOperation(value = "resultApplication : 가족 신청 결과")
	@GetMapping("/result/{userId}")
	public ResponseEntity<?> resultApplication(@PathVariable String userId) throws Exception {
		return ResponseEntity.ok(null);
	}

	@ApiOperation(value = "searchFamily : 가족 정보 조회")
	@GetMapping("/{familyId}")
	public ResponseEntity<?> searchFamily(@PathVariable String familyId) throws Exception {
		return ResponseEntity.ok(null);
	}

	@ApiOperation(value = "searchFamilyList : 가족 콜사인 조회")
	@GetMapping("/list/{familyId}")
	public ResponseEntity<?> searchFamilyList(@PathVariable String familyId) throws Exception {
		return ResponseEntity.ok(null);
	}

	@ApiOperation(value = "updateFamily : 가족 정보 + 콜사인 수정")
	@PutMapping("/{familyId}")
	public ResponseEntity<?> updateFamily(@PathVariable String familyId) throws Exception {
		return ResponseEntity.ok(null);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}