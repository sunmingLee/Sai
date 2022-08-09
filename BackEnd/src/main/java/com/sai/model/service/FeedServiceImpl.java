package com.sai.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.CreateBoardRequestDto;
import com.sai.model.dto.ReadBoardResponseDto;
import com.sai.model.dto.ReadFeedResponseDto;
import com.sai.model.dto.UpdateBoardRequestDto;
import com.sai.model.dto.board.ModifyBoardRequestDto;
import com.sai.model.dto.board.ViewBoardResponseDto;
import com.sai.model.dto.boardMedia.ViewBoardMediaResponseDto;
import com.sai.model.dto.boardTagged.InputBoardTaggedRequestDto;
import com.sai.model.dto.boardTagged.ViewBoardTaggedResponseDto;
import com.sai.model.dto.notification.CreateNotificationRequestDto;
import com.sai.model.dto.poll.PollResponse;
import com.sai.model.dto.reply.ReplyDto;
import com.sai.model.entity.Board;
import com.sai.model.entity.BoardLike;
import com.sai.model.entity.BoardMedia;
import com.sai.model.entity.BoardTagged;
import com.sai.model.entity.Family;
import com.sai.model.entity.NotiType;
import com.sai.model.entity.Poll;
import com.sai.model.entity.Reply;
import com.sai.model.entity.User;
import com.sai.model.repository.BoardLikeRepository;
import com.sai.model.repository.BoardMediaRepository;
import com.sai.model.repository.BoardRepository;
import com.sai.model.repository.BoardTaggedRepository;
import com.sai.model.repository.FamilyRepository;
import com.sai.model.repository.PollRepository;
import com.sai.model.repository.ReplyRepository;
import com.sai.model.repository.UserRepository;
import com.sai.security.CurrentUser;
import com.sai.security.UserPrincipal;

@Service
@Transactional
public class FeedServiceImpl implements FeedService {

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;

	@Autowired
	UserRepository userRepository;
	@Autowired
	FamilyRepository familyRepository;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	ReplyRepository replyRepository;
	@Autowired
	NotificationService notiService;
	@Autowired
	BoardLikeRepository boardLikeRepository;
	@Autowired
	BoardTaggedRepository boardTaggedRepository;
	@Autowired
	BoardMediaRepository boardMediaRepository;
	@Autowired
	PollRepository pollRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	PollService pollService;

	@Override
	public List<ReadFeedResponseDto> readAllBoard(String familyId, String userId, Pageable pageable, UserPrincipal currUser) {
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
				
				Poll poll = pollRepository.findByBoardBoardId(board.getBoardId());
				
				PollResponse pollResponse = pollService.getPollById(poll.getPollId(), currUser);
				readFeedResponseDto.setPollResponse(pollResponse);
				
//				viewPollResponseDto = modelMapper.map(poll, ViewPollResponseDto.class);
//				readFeedResponseDto.setViewPollResponseDto(viewPollResponseDto);
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
			Reply reply = replyRepository.findFirstByBoard(boardRepository.findById(board.getBoardId()).get()).get();
			ReplyDto replyDto = modelMapper.map(reply, ReplyDto.class);
			readFeedResponseDto.setReplyDto(replyDto);

			// List add
			readFeedResponseDtos.add(readFeedResponseDto);
		}

		return readFeedResponseDtos;
	}

	@Override
	public ReadBoardResponseDto readOneBoard(Long boardId, String userId, @CurrentUser UserPrincipal currUser) {
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
		if (board.getPollYn()) {
			Poll poll = pollRepository.findByBoardBoardId(board.getBoardId());
			
			PollResponse pollResponse = pollService.getPollById(poll.getPollId(), currUser);
			readBoardResponseDto.setPollResponse(pollResponse);
			
//			ViewPollResponseDto viewPollResponseDto = new ViewPollResponseDto();
//			Poll poll = pollRepository.findbyBoardId(board.getBoardId());
//			viewPollResponseDto = modelMapper.map(poll, ViewPollResponseDto.class);
//			readBoardResponseDto.setViewPollResponseDto(viewPollResponseDto);
		}

		// 태그 DTO 세팅
		List<ViewBoardTaggedResponseDto> viewBoardTaggedResponseDtos = new ArrayList<>();
		List<BoardTagged> boardTaggeds = boardTaggedRepository.findByBoard(board);
		for (BoardTagged boardTagged : boardTaggeds) {
			viewBoardTaggedResponseDtos.add(modelMapper.map(boardTagged, ViewBoardTaggedResponseDto.class));
		}
		readBoardResponseDto.setViewBoardTaggedResponseDto(viewBoardTaggedResponseDtos);
		
		// 댓글 DTO 세팅
		List<Reply> replyList = replyRepository.findRepliesByBoard(board, null);
		List<ReplyDto> replyDtoList = new ArrayList<>();
		for(Reply reply : replyList) {
			ReplyDto replyDto = modelMapper.map(reply, ReplyDto.class);
			replyDtoList.add(replyDto);
		}
		readBoardResponseDto.setReplies(replyDtoList);

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
	public void writeBoard(CreateBoardRequestDto createBoardRequestDto, List<MultipartFile> files) {
		Board board = modelMapper.map(createBoardRequestDto.getInputBoardRequestDto(), Board.class);
		boardRepository.save(board);

		if (board.getBoardMediaYn()) {

			// 가족아이디로 폴더만들기
			String folderPath = makeFolder(board.getFamily().getFamilyId());

			for (MultipartFile file : files) {
				if (file.isEmpty())
					continue;

				String fileType = file.getContentType();
				String OriginalName = file.getOriginalFilename();
				String fileName = OriginalName.substring(OriginalName.lastIndexOf('\\') + 1);
				String saveName = UUID.randomUUID().toString() + "_" + fileName;
				String savePath = uploadPath + File.separator + folderPath + File.separator + saveName;
				try {
					file.transferTo(Paths.get(savePath));
				} catch (IOException e) {
					e.printStackTrace();
				}

				BoardMedia boardMedia = BoardMedia.builder().board(board).boardMediaPath(savePath)
						.boardMediaOriginalName(OriginalName).boardMediaSaveName(saveName).boardMediaType(fileType)
						.build();
				boardMediaRepository.save(boardMedia);

			}
		}
		
		createBoardRequestDto.getPollRequest().setBoardId(board.getBoardId());

		if (board.getPollYn()) {
			pollService.createPoll(createBoardRequestDto.getPollRequest());
		}

		// 태그된 사람들 저장
		if (board.getBoardTaggedYn()) {
			List<InputBoardTaggedRequestDto> inputBoardTaggedRequestDtos = createBoardRequestDto
					.getInputBoardTaggedRequestDtos();

			for (InputBoardTaggedRequestDto inputBoardTaggedRequestDto : inputBoardTaggedRequestDtos) {
				User user = userRepository.findById(inputBoardTaggedRequestDto.getUserId()).get();
				BoardTagged boardTagged = BoardTagged.builder().board(board).user(user).build();
				
				CreateNotificationRequestDto cnrd = CreateNotificationRequestDto.builder()
																				.notiFromUserId(board.getUser().getUserId())
																				.notiToUserId(user.getUserId())
																				.notiContent("님이 당신을 태그했습니다 !")
																				.notiType(NotiType.TAGGED)
																				.build();
				notiService.createNoti(cnrd);

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
//				System.out.println(deleteBoardMediaId);
				BoardMedia boardMedia = boardMediaRepository.findById(deleteBoardMediaId).get();
				boardMedia.delete();
				boardMediaRepository.delete(boardMedia);
			}
		}

		// 투표 삭제 후 재생성
		if (updateBoardRequestDto.isPollModified()) {
			// 삭제
			Poll poll = pollRepository.findByBoardBoardId(board.getBoardId());
			pollRepository.delete(poll);

			// 재생성
			pollService.createPoll(updateBoardRequestDto.getPollRequest());
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
		List<BoardMedia> boardMedias = boardMediaRepository.findByBoard(board);
		for (BoardMedia boardMedia : boardMedias) {
			boardMedia.delete();
		}

		boardRepository.delete(board);
	}

	@Override
	public void upBoardLike(Long boardId, String userId) {
		Board board = boardRepository.findById(boardId).get();
		User user = userRepository.findById(userId).get();
		
		

		board.upBoardLike();
		
		CreateNotificationRequestDto cnrd =
				CreateNotificationRequestDto.builder()
											.notiToUserId(board.getUser().getUserId())
											.notiFromUserId(userId)
											.notiContent("좋아요를 눌렀습니다.")
											.notiType(NotiType.LIKE)
											.build();
		notiService.createNoti(cnrd);
	
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

	private String makeFolder(String familyId) {
		File uploadPathFolder = new File(uploadPath, familyId);
		if (!uploadPathFolder.exists())
			uploadPathFolder.mkdirs();

		return familyId;
	}
}
