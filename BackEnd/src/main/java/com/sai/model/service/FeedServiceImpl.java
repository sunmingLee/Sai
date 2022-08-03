package com.sai.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.model.dto.CreateBoardRequestDto;
import com.sai.model.dto.ModifyBoardRequestDto;
import com.sai.model.dto.board.UpdateBoardRequestDto;
import com.sai.model.dto.boardMedia.InputBoardMediaRequestDto;
import com.sai.model.dto.boardTagged.InputBoardTaggedRequestDto;
import com.sai.model.entity.Board;
import com.sai.model.entity.BoardLike;
import com.sai.model.entity.BoardMedia;
import com.sai.model.entity.BoardTagged;
import com.sai.model.entity.User;
import com.sai.model.repository.BoardLikeRepository;
import com.sai.model.repository.BoardMediaRepository;
import com.sai.model.repository.BoardRepository;
import com.sai.model.repository.BoardTaggedRepository;
import com.sai.model.repository.FamilyRepository;
import com.sai.model.repository.UserRepository;

@Service
@Transactional
public class FeedServiceImpl implements FeedService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	FamilyRepository familyRepository;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	BoardLikeRepository boardLikeRepository;
	@Autowired
	BoardTaggedRepository boardTaggedRepository;
	@Autowired
	BoardMediaRepository boardMediaRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public void readAllBoard(String familyId, String userId) {
		// TODO Auto-generated method stub
//		return null;
	}

	@Override
	public void readOneBoard(Long boardId, String userId) {
//		BoardOutputDto boardOutputDto = new BoardOutputDto();
//
//		Board board = boardRepository.findById(boardId).get();
//		boardOutputDto.setBoardDto(modelMapper.map(board, BoardDto.class));
//
//		return boardOutputDto;
	}

	@Override
	public void writeBoard(CreateBoardRequestDto createBoardRequestDto) {
		Board board = modelMapper.map(createBoardRequestDto.getInputBoardRequestDto(), Board.class);
		boardRepository.save(board);

		if (board.getBoardMediaYn()) {
			// media save logic
			List<InputBoardMediaRequestDto> inputBoardMediaRequestDtos = createBoardRequestDto
					.getInputBoardMediaRequestDtos();

			for (InputBoardMediaRequestDto inputBoardMediaRequestDto : inputBoardMediaRequestDtos) {

				BoardMedia boardMedia = modelMapper.map(inputBoardMediaRequestDto, BoardMedia.class);
				boardMedia.setBoard(board);

				boardMediaRepository.save(boardMedia);
			}

		}

		if (board.getPollYn()) {
			// poll save logic
		}

		// 태그된 사람들 저장
		if (board.getBoardTaggedYn()) {
			List<InputBoardTaggedRequestDto> inputBoardTaggedRequestDtos = createBoardRequestDto
					.getInputBoardTaggedRequestDtos();

			for (InputBoardTaggedRequestDto inputBoardTaggedRequestDto : inputBoardTaggedRequestDtos) {
				User user = userRepository.findById(inputBoardTaggedRequestDto.getUserId()).get();
				BoardTagged boardTagged = BoardTagged.builder().board(board).user(user).build();

//				BoardTagged boardTagged = modelMapper.map(inputBoardTaggedRequestDto, BoardTagged.class);
//				//	 userId를 boardTaggedId에 넣으려고 해서 에러가 발생
//				boardTagged.setBoard(board);

				boardTaggedRepository.save(boardTagged);
			}
		}
	}

	@Override
	public void updateBoard(ModifyBoardRequestDto modifyBoardRequestDto) {

		UpdateBoardRequestDto updateBoardRequestDto = modifyBoardRequestDto.getUpdateBoardRequestDto();
		Board board = boardRepository.findById(updateBoardRequestDto.getBoardId()).get();

		// 게시글 수정
		if (modifyBoardRequestDto.isBoardModified()) {
			board.updateBoard(updateBoardRequestDto);
			boardRepository.save(board);
		}

		List<Long> deleteBoardMediaIds = modifyBoardRequestDto.getDeleteBoardMediaIds();

		// 미디어 삭제
		if (deleteBoardMediaIds != null) {
			for (Long deleteBoardMediaId : deleteBoardMediaIds) {
				System.out.println(deleteBoardMediaId);
				BoardMedia boardMedia = boardMediaRepository.findById(deleteBoardMediaId).get();
				boardMediaRepository.delete(boardMedia);
			}
		}

		// 투표 삭제 후 재생성
		if (modifyBoardRequestDto.isPollModified()) {
			// logic
		}

		// 태그 삭제 후 재생성
		if (modifyBoardRequestDto.isBoardTaggedModified()) {
			boardTaggedRepository.deleteByBoard(board);

			List<InputBoardTaggedRequestDto> inputBoardTaggedRequestDtos = modifyBoardRequestDto
					.getInputBoardTaggedRequestDtos();

			if (inputBoardTaggedRequestDtos != null)
				for (InputBoardTaggedRequestDto inputBoardTaggedRequestDto : inputBoardTaggedRequestDtos) {
					User user = userRepository.findById(inputBoardTaggedRequestDto.getUserId()).get();
					BoardTagged boardTagged = BoardTagged.builder().board(board).user(user).build();

					boardTaggedRepository.save(boardTagged);
				}
		}
	}

	@Override
	public void deleteBoard(Long boardId) {
		Board board = boardRepository.findById(boardId).get();
		boardRepository.delete(board);
	}

	@Override
	public void upBoardLike(Long boardId, String userId) {
		Board board = boardRepository.findById(boardId).get();
		User user = userRepository.findById(userId).get();

		board.upBoardLike();

		boardLikeRepository.save(BoardLike.builder().board(board).user(user).build());
		boardRepository.save(board);
	}

	@Override
	public void downBoardLike(Long boardId, String userId) {
		Board board = boardRepository.findById(boardId).get();
		User user = userRepository.findById(userId).get();
		BoardLike boardLike = boardLikeRepository.findOneByBoardAndUser(board, user);

		board.downBoardLike();

		boardRepository.save(board);
		boardLikeRepository.delete(boardLike);
	}
}
