package com.sai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.dto.family.FamilyRegisterDto;
import com.sai.model.dto.user.UserDto;
import com.sai.model.service.FamilyService;
import com.sai.model.service.FeedService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/feed")
@CrossOrigin(origins = "*")
public class FeedController {

	@Autowired
	private FeedService feedService;

//	@ApiOperation(value = "createFamilyId : 가족 아이디 생성")
//	@PostMapping("/create/{userId}")
//	public ResponseEntity<?> createFamilyId(@PathVariable String userId) throws Exception {
//
//		try {
//			UserDto userDto = feedService.createFamilyId(userId);
//
//			if (userDto != null) {
//				return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//			}
//
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
//
//	@ApiOperation(value = "applyFamily : 가족 들어가기 신청")
//	@PostMapping("/join/apply")
//	public ResponseEntity<?> applyFamily(@RequestBody FamilyRegisterDto familyRegisterDto) throws Exception {
//
//		try {
//			feedService.applyFamily(familyRegisterDto);
//			return new ResponseEntity<Void>(HttpStatus.OK);
//
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}