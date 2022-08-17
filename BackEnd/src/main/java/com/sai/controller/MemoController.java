package com.sai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.dto.memo.CreateMemoRequestDto;
import com.sai.model.dto.memo.MemoDto;
import com.sai.model.dto.memo.MemoPopupDto;
import com.sai.model.service.MemoService;

@RestController
@RequestMapping(value = "/memo")
@CrossOrigin("*")
public class MemoController {

	@Autowired
	MemoService memoService;
	
	// 메모 등록
	@PostMapping
	public ResponseEntity<?> createMemo(@RequestBody CreateMemoRequestDto createMemoRequestDto){
		try {
			memoService.createMemo(createMemoRequestDto);
			return new ResponseEntity<String>("메모가 등록되었습니다.", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("메모 등록 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 팝업 메모 리스트 조회(유저별)
	@GetMapping(value = "/{familyId}")
	public ResponseEntity<?> getMemoPopup(@RequestBody String userId, @PathVariable String familyId) {
		try {
			List<MemoPopupDto> list = memoService.getMemoPopup(userId, familyId);
			return new ResponseEntity<List<MemoPopupDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 전체 메모 리스트 조회(가족별)
	@GetMapping(value = "/list/{familyId}")
	public ResponseEntity<?> getMemo(@PathVariable String familyId) {
		System.out.println("메모리스트 조회야");
		try {
			List<MemoDto> list = memoService.getMemo(familyId);
			return new ResponseEntity<List<MemoDto>>(list, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 오늘의 메모 리스트 조회(가족별)
	@GetMapping(value = "/today/{familyId}")
	public ResponseEntity<?> getTodayMemo(@PathVariable String familyId) {
		try {
			List<MemoDto> list = memoService.getTodayMemo(familyId);
			return new ResponseEntity<List<MemoDto>>(list, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 메모 리스트 일괄 읽음 처리(유저별)
	@PutMapping
	public ResponseEntity<?> readMemo(@RequestBody String userId){
		try {
			memoService.readMemo(userId);
			return new ResponseEntity<String>("모두 읽음 처리 되었습니다.", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("읽음 처리 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 메모지 컬러 변경
	@PutMapping(value = "/{memoId}")
	public ResponseEntity<?> changeColor(@PathVariable Long memoId, @RequestBody String color){
		try {
			memoService.changeColor(memoId, color);
			return new ResponseEntity<String>("메모지 컬러가 변경되었습니다.", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("메모지 컬러 변경 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 메모 삭제
	@DeleteMapping(value = "/{memoId}")
	public ResponseEntity<?> deleteMemo(@PathVariable Long memoId){
		try {
			memoService.deleteMemo(memoId);
			return new ResponseEntity<String>("메모지가 삭제되었습니다.", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("메모지 삭제 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
