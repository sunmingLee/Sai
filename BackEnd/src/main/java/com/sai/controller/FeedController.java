package com.sai.controller;

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

import com.sai.model.dto.CreateBoardRequestDto;
import com.sai.model.dto.ModifyBoardRequestDto;
import com.sai.model.service.FeedService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/feed")
@CrossOrigin(origins = "*")
public class FeedController {

	@Autowired
	private FeedService feedService;

	@ApiOperation(value = "readAllBoard : 피드 조회")
	@GetMapping("/{familyId}/{userId}")
	public ResponseEntity<?> readAllBoard(@PathVariable String familyId, @PathVariable String userId) throws Exception {

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
		return null;
	}

	@ApiOperation(value = "readOneBoard : 글 상세조회")
	@GetMapping("/board/{boardId}/{userId}")
	public ResponseEntity<?> readOneBoard(@PathVariable Long boardId, @PathVariable String userId) throws Exception {

//		try {
//			BoardOutputDto boardOutputDto = feedService.readOneBoard(boardId, userId);
//
//			if (boardOutputDto != null) {
//				return new ResponseEntity<BoardOutputDto>(boardOutputDto, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//			}
//
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
		return null;
	}

	@ApiOperation(value = "writeBoard : 글 작성하기")
	@PostMapping("/board")
	public ResponseEntity<?> writeBoard(@RequestBody CreateBoardRequestDto createBoardRequestDto) throws Exception {

		try {
			feedService.writeBoard(createBoardRequestDto);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "updateBoard : 글 수정하기")
	@PutMapping("/board")
	public ResponseEntity<?> updateBoard(@RequestBody ModifyBoardRequestDto modifyBoardRequestDto) throws Exception {

		try {
			feedService.updateBoard(modifyBoardRequestDto);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "deleteBoard : 글 삭제하기")
	@DeleteMapping("/board/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) throws Exception {

		try {
			feedService.deleteBoard(boardId);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "upBoardLike : 좋아요 클릭")
	@PostMapping("/board/{boardId}/like/{userId}")
	public ResponseEntity<?> upBoardLike(@PathVariable Long boardId, @PathVariable String userId) throws Exception {

		try {
			feedService.upBoardLike(boardId, userId);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "downBoardLike : 좋아요 취소")
	@DeleteMapping("/board/{boardId}/like/{userId}")
	public ResponseEntity<?> downBoardLike(@PathVariable Long boardId, @PathVariable String userId) throws Exception {

		try {
			feedService.downBoardLike(boardId, userId);
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