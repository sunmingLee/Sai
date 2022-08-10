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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.family.AnswerFamilyRegisterRequestDto;
import com.sai.model.dto.family.FamilyDto;
import com.sai.model.dto.family.FamilyRegisterDto;
import com.sai.model.dto.family.InsertFamilyRegisterRequestDto;
import com.sai.model.dto.family.ReturnFamilyIdDto;
import com.sai.model.dto.family.UpdateFamilyRequestDto;
import com.sai.model.dto.family.ViewFamilyCallsignResponseDto;
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
			String familyId = familyService.createFamilyId(userId);

			if (familyId != null) {

				ReturnFamilyIdDto returnFamilyIdDto = new ReturnFamilyIdDto();
				returnFamilyIdDto.setFamilyId(familyId);

				return new ResponseEntity<ReturnFamilyIdDto>(returnFamilyIdDto, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>("Error : 가족 코드 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<?> applyFamily(@RequestBody InsertFamilyRegisterRequestDto insertFamilyRegisterRequestDto)
			throws Exception {

		try {
			familyService.applyFamily(insertFamilyRegisterRequestDto);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "responseApplication : 가족 신청 수락/거절")
	@PatchMapping("/join/response/{userId}")
	public ResponseEntity<?> responseApplication(@PathVariable String userId,
			@RequestBody AnswerFamilyRegisterRequestDto answerFamilyRegisterRequestDto) throws Exception {

		try {
			List<ViewFamilyCallsignResponseDto> viewFamilyCallsignResponseDtos = familyService
					.responseApplication(userId, answerFamilyRegisterRequestDto);

			if (viewFamilyCallsignResponseDtos != null && viewFamilyCallsignResponseDtos.size() > 0) {
				return new ResponseEntity<List<ViewFamilyCallsignResponseDto>>(viewFamilyCallsignResponseDtos,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

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
			List<ViewFamilyCallsignResponseDto> viewFamilyCallsignResponseDtos = familyService.searchFamilyList(userId);

			if (viewFamilyCallsignResponseDtos != null && viewFamilyCallsignResponseDtos.size() > 0) {
				return new ResponseEntity<List<ViewFamilyCallsignResponseDto>>(viewFamilyCallsignResponseDtos,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "updateFamily : 가족 정보 + 콜사인 수정")
	@PutMapping("/modify")
	public ResponseEntity<?> updateFamily(
//			@RequestBody UpdateFamilyRequestDto updateFamilyRequestDto
			@RequestPart UpdateFamilyRequestDto updateFamilyRequestDto, @RequestPart MultipartFile file)
			throws Exception {

		try {
			familyService.updateFamily(updateFamilyRequestDto, file);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}