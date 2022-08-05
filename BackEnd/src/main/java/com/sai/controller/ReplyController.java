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

import com.sai.model.dto.reply.CreateReplyRequestDto;
import com.sai.model.dto.reply.DeleteReplyRequestDto;
import com.sai.model.dto.reply.GetReplyResponseDto;
import com.sai.model.dto.reply.UpdateReplyRequestDto;
import com.sai.model.service.ReplyService;

@RestController
@RequestMapping(value = "/feed")
@CrossOrigin("*")
public class ReplyController {

	@Autowired
	ReplyService replyService;

	/*
	 * // 댓글 작성 api // @PostMapping("{id}/reply") // public ResponseEntity<String>
	 * createReply(@PathVariable Long id, @RequestBody Reply reply) { ////
	 * System.out.println(id); //// System.out.println(reply.toString()); //
	 * replyService.createReply(id, reply); // return ResponseEntity.ok("성공했습니다");
	 * // }
	 * 
	 */

	@PostMapping("{id}/reply")
	public ResponseEntity<?> createReply(@PathVariable Long id,
			@RequestBody CreateReplyRequestDto createReplyRequestDTO) {
		ResponseEntity<String> response;

		String resultMessage = replyService.createReply(id, createReplyRequestDTO);
		if (resultMessage.equals("success")) {
			response = new ResponseEntity<String>(resultMessage, HttpStatus.CREATED);
		} else {
			response = new ResponseEntity<String>(resultMessage, HttpStatus.EXPECTATION_FAILED);
		}
		return response;
		
	}

	// 댓글 조회 api
	@GetMapping("/{id}/reply")
	public ResponseEntity<?> getReply(@PathVariable Long id) throws Exception {
		try {
			GetReplyResponseDto response = replyService.getReply(id);
			return new ResponseEntity<GetReplyResponseDto>(response, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return exceptionHandling(e);
		} finally {
			System.out.println("");
		}
	}

	// 댓글 삭제 api
	@DeleteMapping("{id}/reply")
	public ResponseEntity<String> deleteReply(@PathVariable Long id,
			@RequestBody DeleteReplyRequestDto deleteReplyRequestDTO) throws Exception {
		try {
			// 요청한 유저 id와 댓글 작성자의 아이디 일치 여부 검사
			if (replyService.getUserIdByReplyId(deleteReplyRequestDTO.getReplyId())
					.equals(deleteReplyRequestDTO.getUserId())) {
				replyService.deleteReplyByReplyId(deleteReplyRequestDTO.getReplyId());
			} else
				throw new Exception();
		} catch (Exception e) {
			return exceptionHandling(e);
		}

		return new ResponseEntity<String>("댓글이 삭제되었습니다.", HttpStatus.OK);

	}

	// 댓글 수정 api
	@PutMapping("{id}/reply")
	public ResponseEntity<String> updateReply(@PathVariable Long id,
			@RequestBody UpdateReplyRequestDto updateReplyRequestDTO) throws Exception {
		try {
			// 요청한 유저 id와 댓글 작성자의 아이디 일치 여부 검사
			if (replyService.getUserIdByReplyId(updateReplyRequestDTO.getReplyId())
					.equals(updateReplyRequestDTO.getUserId())) {
				replyService.updateReply(updateReplyRequestDTO);
			} else
				throw new Exception();
		} catch (Exception e) {
			return exceptionHandling(e);
		}

		return new ResponseEntity<String>("댓글이 수정되었습니다.", HttpStatus.OK);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
