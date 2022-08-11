package com.sai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.CreateBoardRequestDto;
import com.sai.model.dto.ReadBoardResponseDto;
import com.sai.model.dto.ReadFeedResponseDto;
import com.sai.model.dto.UpdateBoardRequestDto;
import com.sai.model.service.FeedService;
import com.sai.security.CurrentUser;
import com.sai.security.UserPrincipal;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/feed")
@CrossOrigin(origins = "*")
public class FeedController {

	@Autowired
	private FeedService feedService;

	@ApiOperation(value = "readAllBoard : 피드 조회")
	@GetMapping("/{familyId}/{userId}")
	public ResponseEntity<?> readAllBoard(@PathVariable String familyId, @PathVariable String userId,
            @PageableDefault(size = 3, sort = "boardRegDatetime", direction = Direction.DESC) Pageable pageable, @CurrentUser UserPrincipal currUser)
			throws Exception {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			System.out.println(principal.toString());

//			String username = userPrincipal.getUsername();
//			System.out.println(username);
			
			List<ReadFeedResponseDto> readFeedResponseDtos = feedService.readAllBoard(familyId, userId, pageable, currUser);

			if (readFeedResponseDtos != null) {
				return new ResponseEntity<List<ReadFeedResponseDto>>(readFeedResponseDtos, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "readOneBoard : 글 상세조회")
	@GetMapping("/board/{boardId}/{userId}")
	public ResponseEntity<?> readOneBoard(@PathVariable Long boardId, @PathVariable String userId, @CurrentUser UserPrincipal currUser) throws Exception {

		try {
			ReadBoardResponseDto readBoardResponseDto = feedService.readOneBoard(boardId, userId, currUser);

			if (readBoardResponseDto != null) {
				return new ResponseEntity<ReadBoardResponseDto>(readBoardResponseDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "writeBoard : 글 작성하기")
	@PostMapping("/board")
	public ResponseEntity<?> writeBoard(
//			@RequestBody CreateBoardRequestDto createBoardRequestDto
			@RequestPart CreateBoardRequestDto createBoardRequestDto
			,@RequestPart(name = "files", required = false) List<MultipartFile> files
//			,@RequestParam(name = "files", required = false) List<MultipartFile> files
	) throws Exception {

		try {
			feedService.writeBoard(createBoardRequestDto, files);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "updateBoard : 글 수정하기")
	@PutMapping("/board")
	public ResponseEntity<?> updateBoard(@RequestBody UpdateBoardRequestDto updateBoardRequestDto) throws Exception {

		try {
			feedService.updateBoard(updateBoardRequestDto);
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