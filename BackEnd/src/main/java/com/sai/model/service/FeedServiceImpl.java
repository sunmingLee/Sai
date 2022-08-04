package com.sai.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sai.model.dto.CreateBoardRequestDto;
import com.sai.model.dto.ReadBoardResponseDto;
import com.sai.model.dto.ReadFeedResponseDto;
import com.sai.model.dto.UpdateBoardRequestDto;
import com.sai.model.dto.board.ModifyBoardRequestDto;
import com.sai.model.dto.board.ViewBoardResponseDto;
import com.sai.model.dto.boardMedia.InputBoardMediaRequestDto;
import com.sai.model.dto.boardMedia.ViewBoardMediaResponseDto;
import com.sai.model.dto.boardTagged.InputBoardTaggedRequestDto;
import com.sai.model.dto.boardTagged.ViewBoardTaggedResponseDto;
import com.sai.model.entity.Board;
import com.sai.model.entity.BoardLike;
import com.sai.model.entity.BoardMedia;
import com.sai.model.entity.BoardTagged;
import com.sai.model.entity.Family;
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
	public List<ReadFeedResponseDto> readAllBoard(String familyId, String userId, Pageable pageable) {
		List<ReadFeedResponseDto> readFeedResponseDtos = new ArrayList<>();
		Family family = familyRepository.findById(familyId).get();
		User user = userRepository.findById(userId).get();

		List<Board> boards = boardRepository.findByFamily(family, pageable);
		for (Board board : boards) {
			ReadFeedResponseDto readFeedResponseDto = new ReadFeedResponseDto();

			// 게시글 DTO 세팅
			readFeedResponseDto.setViewBoardResponseDto(modelMapper.map(board, ViewBoardResponseDto.class));

			// 투표 관련 DTO 세팅
			if (board.getPollYn()) {

			}

			// 게시글 미디어 DTO 세팅
			else if (board.getBoardMediaYn()) {
				List<ViewBoardMediaResponseDto> viewBoardMediaResponseDtos = new ArrayList<>();
				List<BoardMedia> boardMedias = boardMediaRepository.findByBoard(board);
				for (BoardMedia boardMedia : boardMedias) {
					viewBoardMediaResponseDtos.add(modelMapper.map(boardMedia, ViewBoardMediaResponseDto.class));
				}
				readFeedResponseDto.setViewBoardMediaResponseDto(viewBoardMediaResponseDtos);
			}

			// 좋아요 여부 세팅
			BoardLike boardLike = boardLikeRepository.findOneByBoardAndUser(board, user);
			if (boardLike == null)
				readFeedResponseDto.setBoardLiked(false);
			else
				readFeedResponseDto.setBoardLiked(true);

			// 댓글 DTO 1개 세팅

			// List add
			readFeedResponseDtos.add(readFeedResponseDto);
		}

		return readFeedResponseDtos;
	}

	@Override
	public ReadBoardResponseDto readOneBoard(Long boardId, String userId) {
		ReadBoardResponseDto readBoardResponseDto = new ReadBoardResponseDto();

		// 게시글 DTO 세팅
		Board board = boardRepository.findById(boardId).get();
		readBoardResponseDto.setViewBoardResponseDto(modelMapper.map(board, ViewBoardResponseDto.class));

		// 게시글 미디어 DTO 세팅
		List<ViewBoardMediaResponseDto> viewBoardMediaResponseDtos = new ArrayList<>();
		List<BoardMedia> boardMedias = boardMediaRepository.findByBoard(board);
		for (BoardMedia boardMedia : boardMedias) {
			viewBoardMediaResponseDtos.add(modelMapper.map(boardMedia, ViewBoardMediaResponseDto.class));
		}
		readBoardResponseDto.setViewBoardMediaResponseDto(viewBoardMediaResponseDtos);

		// 투표 관련 DTO 세팅

		// 태그 DTO 세팅
		List<ViewBoardTaggedResponseDto> viewBoardTaggedResponseDtos = new ArrayList<>();
		List<BoardTagged> boardTaggeds = boardTaggedRepository.findByBoard(board);
		for (BoardTagged boardTagged : boardTaggeds) {
			viewBoardTaggedResponseDtos.add(modelMapper.map(boardTagged, ViewBoardTaggedResponseDto.class));
		}
		readBoardResponseDto.setViewBoardTaggedResponseDto(viewBoardTaggedResponseDtos);

		// 좋아요 여부 세팅
		User user = userRepository.findById(userId).get();
		BoardLike boardLike = boardLikeRepository.findOneByBoardAndUser(board, user);
		if (boardLike == null)
			readBoardResponseDto.setBoardLiked(false);
		else
			readBoardResponseDto.setBoardLiked(true);

		return readBoardResponseDto;
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
	public void updateBoard(UpdateBoardRequestDto updateBoardRequestDto) {

		ModifyBoardRequestDto modifyBoardRequestDto = updateBoardRequestDto.getModifyBoardRequestDto();
		Board board = boardRepository.findById(modifyBoardRequestDto.getBoardId()).get();

		// 게시글 수정
		if (updateBoardRequestDto.isBoardModified()) {
			board.updateBoard(modifyBoardRequestDto);
			boardRepository.save(board);
		}

		List<Long> deleteBoardMediaIds = updateBoardRequestDto.getDeleteBoardMediaIds();

		// 미디어 삭제
		if (deleteBoardMediaIds != null) {
			for (Long deleteBoardMediaId : deleteBoardMediaIds) {
				System.out.println(deleteBoardMediaId);
				BoardMedia boardMedia = boardMediaRepository.findById(deleteBoardMediaId).get();
				boardMediaRepository.delete(boardMedia);
			}
		}

		// 투표 삭제 후 재생성
		if (updateBoardRequestDto.isPollModified()) {
			// logic
		}

		// 태그 삭제 후 재생성
		if (updateBoardRequestDto.isBoardTaggedModified()) {
			boardTaggedRepository.deleteByBoard(board);

			List<InputBoardTaggedRequestDto> inputBoardTaggedRequestDtos = updateBoardRequestDto
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
