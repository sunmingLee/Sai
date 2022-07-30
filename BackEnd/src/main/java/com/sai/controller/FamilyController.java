package com.sai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.dto.family.FamilyCallsignDto;
import com.sai.model.dto.family.FamilyDto;
import com.sai.model.dto.family.FamilyRegisterDto;
import com.sai.model.dto.family.UpdateFamilyVo;
import com.sai.model.service.FamilyService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/family")
@CrossOrigin(origins = "*")
public class FamilyController {

	@Autowired
	private FamilyService familyService;

	@ApiOperation(value = "createFamilyId : 가족 아이디 생성")
	@PostMapping("/create/{userId}")
	public ResponseEntity<?> createFamilyId(@PathVariable String userId) throws Exception {

		try {
			FamilyDto familyDto = familyService.createFamilyId(userId);

			if (familyDto != null) {
				return new ResponseEntity<FamilyDto>(familyDto, HttpStatus.OK);
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
			familyService.disjoinFamily(userId);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "applyFamily : 가족 들어가기 신청")
	@PostMapping("/join/apply")
	public ResponseEntity<?> applyFamily(@RequestBody FamilyRegisterDto familyRegisterDto) throws Exception {

		try {
			familyService.applyFamily(familyRegisterDto);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "responseApplication : 가족 신청 수락/거절")
	@PatchMapping("/join/response")
	public ResponseEntity<?> responseApplication(@RequestBody FamilyRegisterDto familyRegisterDto) throws Exception {

		try {
			familyService.responseApplication(familyRegisterDto);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "resultApplication : 가족 신청 결과")
	@GetMapping("/join/{userId}")
	public ResponseEntity<?> resultApplication(@PathVariable String userId) throws Exception {

		try {
			FamilyRegisterDto familyRegisterDto = familyService.resultApplication(userId);

			if (familyRegisterDto != null) {
				return new ResponseEntity<FamilyRegisterDto>(familyRegisterDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "searchFamily : 가족 신청 취소")
	@DeleteMapping("/join/{userId}")
	public ResponseEntity<?> deleteApplication(@PathVariable String userId) throws Exception {

		try {
			familyService.deleteApplication(userId);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "searchFamily : 가족 정보 조회")
	@GetMapping("/{familyId}")
	public ResponseEntity<?> searchFamily(@PathVariable String familyId) throws Exception {

		try {
			FamilyDto familyDto = familyService.searchFamily(familyId);

			if (familyDto != null) {
				return new ResponseEntity<FamilyDto>(familyDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "searchFamilyList : 가족 콜사인 조회")
	@GetMapping("/list/{userId}")
	public ResponseEntity<?> searchFamilyList(@PathVariable String userId) throws Exception {

		try {
			List<FamilyCallsignDto> familyCallsignDtos = familyService.searchFamilyList(userId);

			if (familyCallsignDtos != null && familyCallsignDtos.size() > 0) {
				return new ResponseEntity<List<FamilyCallsignDto>>(familyCallsignDtos, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "updateFamily : 가족 정보 + 콜사인 수정")
	@PutMapping("/modify")
	public ResponseEntity<?> updateFamily(@RequestBody UpdateFamilyVo updateFamilyVo) throws Exception {

		try {
			UpdateFamilyVo returnUpdateFamilyVo = familyService.updateFamily(updateFamilyVo);

			if (returnUpdateFamilyVo != null) {
				return new ResponseEntity<UpdateFamilyVo>(returnUpdateFamilyVo, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}