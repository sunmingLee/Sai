package com.sai.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.sai.model.dto.poll.PollRequest;
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

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
@Transactional
public class FeedServiceImpl implements FeedService {

	private String uploadPath = File.separator + "app" + File.separator + "Feed";
	private String frontPath = File.separator + "saimedia" + File.separator + "Feed";

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
	public List<ReadFeedResponseDto> readAllBoard(String familyId, String userId, Pageable pageable,
			UserPrincipal currUser) {
		List<ReadFeedResponseDto> readFeedResponseDtos = new ArrayList<>();
		Family family = familyRepository.findById(familyId).get();
		User user = userRepository.findById(userId).get();

		List<Board> boards = boardRepository.findByFamily(family, pageable);
		for (Board board : boards) {
			ReadFeedResponseDto readFeedResponseDto = new ReadFeedResponseDto();

			// ????????? DTO ??????
			readFeedResponseDto.setViewBoardResponseDto(modelMapper.map(board, ViewBoardResponseDto.class));

			// ?????? ?????? DTO ??????
			if (board.getPollYn()) {

				Poll poll = pollRepository.findByBoardBoardId(board.getBoardId());

				PollResponse pollResponse = pollService.getPollById(poll.getPollId(), currUser);
				readFeedResponseDto.setPollResponse(pollResponse);

//				viewPollResponseDto = modelMapper.map(poll, ViewPollResponseDto.class);
//				readFeedResponseDto.setViewPollResponseDto(viewPollResponseDto);
			}

			// ????????? ????????? DTO ??????
			else if (board.getBoardMediaYn()) {
				List<ViewBoardMediaResponseDto> viewBoardMediaResponseDtos = new ArrayList<>();
				List<BoardMedia> boardMedias = boardMediaRepository.findByBoard(board);
				for (BoardMedia boardMedia : boardMedias) {
					viewBoardMediaResponseDtos.add(modelMapper.map(boardMedia, ViewBoardMediaResponseDto.class));
				}
				readFeedResponseDto.setViewBoardMediaResponseDto(viewBoardMediaResponseDtos);
			}

			// ????????? ?????? ??????
			if (board.getBoardLikeCnt() != 0) {
				BoardLike boardLike = boardLikeRepository.findOneByBoardAndUser(board, user);
				if (boardLike == null)
					readFeedResponseDto.setBoardLiked(false);
				else
					readFeedResponseDto.setBoardLiked(true);
			} else
				readFeedResponseDto.setBoardLiked(false);

			// ?????? DTO 1??? ??????
			if (board.getBoardReplyCnt() != 0) {
				Reply reply = replyRepository.findFirstByBoard(boardRepository.findById(board.getBoardId()).get())
						.get();
				ReplyDto replyDto = modelMapper.map(reply, ReplyDto.class);
				readFeedResponseDto.setReplyDto(replyDto);
			}
			// List add
			readFeedResponseDtos.add(readFeedResponseDto);
		}

		return readFeedResponseDtos;
	}

	@Override
	public ReadBoardResponseDto readOneBoard(Long boardId, String userId, @CurrentUser UserPrincipal currUser) {
		ReadBoardResponseDto readBoardResponseDto = new ReadBoardResponseDto();

		// ????????? DTO ??????
		Board board = boardRepository.findById(boardId).get();
		readBoardResponseDto.setViewBoardResponseDto(modelMapper.map(board, ViewBoardResponseDto.class));

		// ????????? ????????? DTO ??????
		List<ViewBoardMediaResponseDto> viewBoardMediaResponseDtos = new ArrayList<>();
		List<BoardMedia> boardMedias = boardMediaRepository.findByBoard(board);
		for (BoardMedia boardMedia : boardMedias) {
			viewBoardMediaResponseDtos.add(modelMapper.map(boardMedia, ViewBoardMediaResponseDto.class));
		}
		readBoardResponseDto.setViewBoardMediaResponseDto(viewBoardMediaResponseDtos);

		// ?????? ?????? DTO ??????
		if (board.getPollYn()) {
			Poll poll = pollRepository.findByBoardBoardId(board.getBoardId());

			PollResponse pollResponse = pollService.getPollById(poll.getPollId(), currUser);
			readBoardResponseDto.setPollResponse(pollResponse);

//			ViewPollResponseDto viewPollResponseDto = new ViewPollResponseDto();
//			Poll poll = pollRepository.findbyBoardId(board.getBoardId());
//			viewPollResponseDto = modelMapper.map(poll, ViewPollResponseDto.class);
//			readBoardResponseDto.setViewPollResponseDto(viewPollResponseDto);
		}

		// ?????? DTO ??????
		List<ViewBoardTaggedResponseDto> viewBoardTaggedResponseDtos = new ArrayList<>();
		List<BoardTagged> boardTaggeds = boardTaggedRepository.findByBoard(board);
		for (BoardTagged boardTagged : boardTaggeds) {
			viewBoardTaggedResponseDtos.add(modelMapper.map(boardTagged, ViewBoardTaggedResponseDto.class));
		}
		readBoardResponseDto.setViewBoardTaggedResponseDto(viewBoardTaggedResponseDtos);

		// ?????? DTO ??????
		List<Reply> replyList = replyRepository.findRepliesByBoard(board, null);
		List<ReplyDto> replyDtoList = new ArrayList<>();
		for (Reply reply : replyList) {
			ReplyDto replyDto = modelMapper.map(reply, ReplyDto.class);
			replyDtoList.add(replyDto);
		}
		readBoardResponseDto.setReplies(replyDtoList);

		// ????????? ?????? ??????
		User user = userRepository.findById(userId).get();

		if (board.getBoardLikeCnt() != 0) {
			BoardLike boardLike = boardLikeRepository.findOneByBoardAndUser(board, user);
			if (boardLike == null)
				readBoardResponseDto.setBoardLiked(false);
			else
				readBoardResponseDto.setBoardLiked(true);
		} else
			readBoardResponseDto.setBoardLiked(false);

		return readBoardResponseDto;
	}

	@Override
	public void writeBoard(CreateBoardRequestDto createBoardRequestDto, List<MultipartFile> files) {
		Board board = modelMapper.map(createBoardRequestDto.getInputBoardRequestDto(), Board.class);
		board.makeLikeReplyCntZero();

		boardRepository.save(board);

		if (board.getBoardMediaYn()) {

			// ?????????????????? ???????????????
			String folderPath = makeFolder(board.getFamily().getFamilyId());

			for (MultipartFile file : files) {
				if (file.isEmpty())
					continue;

				String fileType = file.getContentType();
				String OriginalName = file.getOriginalFilename();
				String fileName = OriginalName.substring(OriginalName.lastIndexOf('\\') + 1);
				String saveName = UUID.randomUUID().toString() + "_" + fileName;
				String savePath = uploadPath + File.separator + folderPath + File.separator + saveName;
				String thumbnailPath = uploadPath + File.separator + folderPath + File.separator + "th_" + saveName;
				String frontSavePath = frontPath + File.separator + folderPath + File.separator + saveName;
				String frontThumbnailPath = frontPath + File.separator + folderPath + File.separator + "th_" + saveName;

				try {

					Path path = Paths.get(savePath);
					file.transferTo(path);
					File thumbnailFile = new File(thumbnailPath);
					Thumbnails.of(path.toFile()).size(400, 400).crop(Positions.CENTER).toFile(thumbnailFile);

				} catch (IOException e) {
					e.printStackTrace();
				}

				BoardMedia boardMedia = BoardMedia.builder().board(board).boardMediaPath(frontSavePath)
						.boardMediaPathServer(savePath).boardMediaOriginalName(OriginalName).boardMediaType(fileType)
						.boardMediaThumbnail(frontThumbnailPath).boardMediaThumbServer(thumbnailPath).build();
				boardMediaRepository.save(boardMedia);

			}
		}

		if (board.getPollYn()) {
			createBoardRequestDto.getPollRequest().setBoardId(board.getBoardId());
			pollService.createPoll(createBoardRequestDto.getPollRequest());
		}

		// ????????? ????????? ??????
		if (board.getBoardTaggedYn()) {
			List<InputBoardTaggedRequestDto> inputBoardTaggedRequestDtos = createBoardRequestDto
					.getInputBoardTaggedRequestDtos();
					
					for (InputBoardTaggedRequestDto inputBoardTaggedRequestDto : inputBoardTaggedRequestDtos) {
						User user = userRepository.findById(inputBoardTaggedRequestDto.getUserId()).get();
						BoardTagged boardTagged = BoardTagged.builder().board(board).user(user).build();
						
						boardTaggedRepository.save(boardTagged);
				if (createBoardRequestDto.getInputBoardRequestDto().getUserId()
						.equals(inputBoardTaggedRequestDto.getUserId()))
					continue;

				CreateNotificationRequestDto cnrd = CreateNotificationRequestDto.builder()
						.notiFromUserId(board.getUser().getUserId()).notiToUserId(user.getUserId())
						.notiContent("?????? ????????? ?????????????????? !").notiContentId(Long.toString(board.getBoardId()))
						.notiType(NotiType.TAGGED).build();
				notiService.createNoti(cnrd);

//				BoardTagged boardTagged = modelMapper.map(inputBoardTaggedRequestDto, BoardTagged.class);
//				//	 userId??? boardTaggedId??? ???????????? ?????? ????????? ??????
//				boardTagged.setBoard(board);

			}
		}
	}

	@Override
	public void updateBoard(UpdateBoardRequestDto updateBoardRequestDto) {

		ModifyBoardRequestDto modifyBoardRequestDto = updateBoardRequestDto.getModifyBoardRequestDto();
		Board board = boardRepository.findById(modifyBoardRequestDto.getBoardId()).get();

		// ????????? ??????
		if (updateBoardRequestDto.isBoardModified()) {
			board.updateBoard(modifyBoardRequestDto);
			boardRepository.save(board);
		}

		List<Long> deleteBoardMediaIds = updateBoardRequestDto.getDeleteBoardMediaIds();

		// ????????? ??????
		if (deleteBoardMediaIds != null) {
			for (Long deleteBoardMediaId : deleteBoardMediaIds) {
				BoardMedia boardMedia = boardMediaRepository.findById(deleteBoardMediaId).get();
				boardMedia.delete();
				boardMediaRepository.delete(boardMedia);
			}
		}

		if (modifyBoardRequestDto.getPollYn()) {

			// ?????? ?????? ??? ?????????
			if (updateBoardRequestDto.isPollModified()) {
				// ??????
				Poll poll = pollRepository.findByBoardBoardId(board.getBoardId());

				// ????????? ?????? ??????
				if (poll != null) {
					pollRepository.delete(poll);

					// ?????????
					PollRequest pollRequest = updateBoardRequestDto.getPollRequest();
					if (pollRequest != null) {
						pollService.createPoll(pollRequest);
					}
				}
			}
			// ????????? ?????? ?????? ?????? ?????????
			else {
				PollRequest pollRequest = updateBoardRequestDto.getPollRequest();
				if (pollRequest != null) {
					pollService.createPoll(pollRequest);
				}
			}

			// ?????? ?????? ??? ?????????
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

		if (!userId.equals(board.getUser().getUserId())) {
			CreateNotificationRequestDto cnrd = CreateNotificationRequestDto.builder()
					.notiToUserId(board.getUser().getUserId()).notiFromUserId(userId).notiContent("???????????? ???????????????.")
					.notiContentId(Long.toString(boardId)).notiType(NotiType.LIKE).build();
			notiService.createNoti(cnrd);
		}

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

	// ??????????????? ?????? ?????? ??????
	@Override
	public List<ReadFeedResponseDto> readAllBoard(String userId, Pageable pageable, UserPrincipal currUser) {
		List<ReadFeedResponseDto> readFeedResponseDtos = new ArrayList<>();
		User user = userRepository.findById(userId).get();

		List<Board> boards = boardRepository.findByUser(user, pageable);
		for (Board board : boards) {
			ReadFeedResponseDto readFeedResponseDto = new ReadFeedResponseDto();

			// ????????? DTO ??????
			readFeedResponseDto.setViewBoardResponseDto(modelMapper.map(board, ViewBoardResponseDto.class));

			// ?????? ?????? DTO ??????
			if (board.getPollYn()) {

				Poll poll = pollRepository.findByBoardBoardId(board.getBoardId());

				PollResponse pollResponse = pollService.getPollById(poll.getPollId(), currUser);
				readFeedResponseDto.setPollResponse(pollResponse);

//				viewPollResponseDto = modelMapper.map(poll, ViewPollResponseDto.class);
//				readFeedResponseDto.setViewPollResponseDto(viewPollResponseDto);
			}

			// ????????? ????????? DTO ??????
			else if (board.getBoardMediaYn()) {
				List<ViewBoardMediaResponseDto> viewBoardMediaResponseDtos = new ArrayList<>();
				List<BoardMedia> boardMedias = boardMediaRepository.findByBoard(board);
				for (BoardMedia boardMedia : boardMedias) {
					viewBoardMediaResponseDtos.add(modelMapper.map(boardMedia, ViewBoardMediaResponseDto.class));
				}
				readFeedResponseDto.setViewBoardMediaResponseDto(viewBoardMediaResponseDtos);
			}

			// ????????? ?????? ??????
//			BoardLike boardLike = boardLikeRepository.findOneByBoardAndUser(board, user);
//			if (boardLike == null)
//				readFeedResponseDto.setBoardLiked(false);
//			else
//				readFeedResponseDto.setBoardLiked(true);

			// ?????? DTO 1??? ??????
//			 if(board.getBoardReplyCnt() != 0) {
//			Reply reply = replyRepository.findFirstByBoard(boardRepository.findById(board.getBoardId()).get()).get();
//			ReplyDto replyDto = modelMapper.map(reply, ReplyDto.class);
//			readFeedResponseDto.setReplyDto(replyDto);
//			 }
			// List add
			readFeedResponseDtos.add(readFeedResponseDto);
		}

		return readFeedResponseDtos;
	}

	@Override
	public Integer countAllBoard(String userId) {
		User user = userRepository.findById(userId).get();
		return boardRepository.countByUser(user);
	}
}
