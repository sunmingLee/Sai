package com.sai.model.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.family.AnswerFamilyRegisterRequestDto;
import com.sai.model.dto.family.FamilyDto;
import com.sai.model.dto.family.FamilyRegisterDto;
import com.sai.model.dto.family.InsertFamilyRegisterRequestDto;
import com.sai.model.dto.family.UpdateFamilyCallsignDto;
import com.sai.model.dto.family.UpdateFamilyDto;
import com.sai.model.dto.family.UpdateFamilyRequestDto;
import com.sai.model.dto.family.ViewFamilyCallsignResponseDto;
import com.sai.model.dto.notification.CreateNotificationRequestDto;
import com.sai.model.entity.Family;
import com.sai.model.entity.FamilyCallsign;
import com.sai.model.entity.FamilyRegister;
import com.sai.model.entity.NotiType;
import com.sai.model.entity.User;
import com.sai.model.repository.FamilyCallsignRepository;
import com.sai.model.repository.FamilyRegisterRepository;
import com.sai.model.repository.FamilyRepository;
import com.sai.model.repository.UserRepository;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

	private String uploadPath = File.separator + "app" + File.separator + "FamilyImage";
	private String frontPath = File.separator + "saimedia" + File.separator + "FamilyImage";

	@Autowired
	UserRepository userRepository;

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	FamilyRegisterRepository familyRegisterRepository;

	@Autowired
	FamilyCallsignRepository familyCallsignRepository;

	@Autowired
	NotificationService notiService;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public String createFamilyId(String userId) {
		String familyId;
		while (true) {
			familyId = createRandomFamilyId();
			if (!familyRepository.existsById(familyId))
				break;
		}

		Family family = Family.builder().familyId(familyId).familyName("?????? ??????").build();
		User user = userRepository.findById(userId).get();
		family.addUser(user);
		userRepository.save(user);
		familyRepository.save(family);

		// ??? ??????
		FamilyCallsign callsign = FamilyCallsign.builder().fromUser(user).toUser(user).callsign(user.getUserName())
				.build();
		familyCallsignRepository.save(callsign);

		return familyId;

	}

	@Override
	public void disjoinFamily(String userId) {
		User user = userRepository.findById(userId).get();
		Family family = user.getFamily();

		// ????????? ??????
		familyCallsignRepository.deleteByFromUser(user);
		familyCallsignRepository.deleteByToUser(user);

		user.setFamily(null);
		userRepository.save(user);

		// ????????? 0????????? ?????? ??????
		if (family.getUsers().size() == 0) {
			family.deleteImage();
			familyRepository.delete(family);
		}
	}

	@Override
	public void applyFamily(InsertFamilyRegisterRequestDto insertFamilyRegisterRequestDto) {
		FamilyRegister familyRegister = modelMapper.map(insertFamilyRegisterRequestDto, FamilyRegister.class);
		Family family = familyRepository.findById(insertFamilyRegisterRequestDto.getFamilyId()).get();

//		familyRegisterRepository.save(modelMapper.map(insertFamilyRegisterRequestDto, FamilyRegister.class));
		familyRegisterRepository.saveAndFlush(familyRegister);

		familyRegister = familyRegisterRepository
				.findOneByUser(userRepository.findByUserId(insertFamilyRegisterRequestDto.getUserId()).get());

		List<User> userList = family.getUsers();
		for (User user : userList) {
			CreateNotificationRequestDto cnrd = CreateNotificationRequestDto.builder()
					.notiFromUserId(insertFamilyRegisterRequestDto.getUserId()).notiToUserId(user.getUserId())
					.notiContent("?????? ????????? ????????????????").notiType(NotiType.FAMILYREGISTER)
					.notiContentId(Long.toString(familyRegister.getFamilyRegisterId())).build();
			notiService.createNoti(cnrd);
		}
	}

	@Override
	public List<ViewFamilyCallsignResponseDto> responseApplication(String userId,
			AnswerFamilyRegisterRequestDto answerFamilyRegisterRequestDto) {
		FamilyRegister findFamilyRegister = familyRegisterRepository
				.findById(answerFamilyRegisterRequestDto.getFamilyRegisterId()).get();
		if (answerFamilyRegisterRequestDto.getApproveYn()) { // ???????????? ????????? ??????, ?????? ?????? ??????, ?????? ????????? ??????

			Family family = findFamilyRegister.getFamily();
			User user = findFamilyRegister.getUser();

			// 1. ?????? ?????? ??????

			List<User> toUsers = family.getUsers();
			for (User toUser : toUsers) {
//				if (user.getUserId().equals(toUser.getUserId()))
//					continue;
				// ?????? ????????? ??????
				FamilyCallsign callsign1 = FamilyCallsign.builder().fromUser(user).toUser(toUser)
						.callsign(toUser.getUserName()).build();
				familyCallsignRepository.save(callsign1);
				// ?????? ????????? ??????
				FamilyCallsign callsign2 = FamilyCallsign.builder().fromUser(toUser).toUser(user)
						.callsign(user.getUserName()).build();
				familyCallsignRepository.save(callsign2);
			}
			// ??? ??????
			FamilyCallsign callsign3 = FamilyCallsign.builder().fromUser(user).toUser(user).callsign(user.getUserName())
					.build();
			familyCallsignRepository.save(callsign3);

			// 2. ?????? ??????ID ??????
			family.addUser(user);
			userRepository.save(user);
			familyRepository.save(family);

			// 3. ?????? ????????? ??????
			familyRegisterRepository.delete(findFamilyRegister);

		} else { // ????????? ??????
			findFamilyRegister.updateResponse(answerFamilyRegisterRequestDto.getApproveYn());
			familyRegisterRepository.save(findFamilyRegister);
		}

		return searchFamilyList(userId);
	}

	@Override
	public FamilyRegisterDto resultApplication(String userId) {

		User user = userRepository.findById(userId).get();
		return modelMapper.map(familyRegisterRepository.findOneByUser(user), FamilyRegisterDto.class);
	}

	@Override
	public void deleteApplication(String userId) {
		User user = userRepository.findById(userId).get();
		FamilyRegister familyRegister = familyRegisterRepository.findOneByUser(user);
		familyRegisterRepository.delete(familyRegister);
	}

	@Override
	public FamilyDto searchFamily(String familyId) {
		return modelMapper.map(familyRepository.findById(familyId).get(), FamilyDto.class);
	}

	@Override
	public List<ViewFamilyCallsignResponseDto> searchFamilyList(String userId) {
		User user = userRepository.findById(userId).get();
		List<FamilyCallsign> familyCallsigns = familyCallsignRepository.findByFromUser(user);

		List<ViewFamilyCallsignResponseDto> list = new ArrayList<>();
		for (FamilyCallsign familyCallsign : familyCallsigns) {

			ViewFamilyCallsignResponseDto viewFamilyCallsignResponseDto = modelMapper.map(familyCallsign,
					ViewFamilyCallsignResponseDto.class);

			User toUser = familyCallsign.getToUser();
			viewFamilyCallsignResponseDto.setToUserName(toUser.getUserName());
			viewFamilyCallsignResponseDto.setToUserImage(toUser.getUserImagePath());

			list.add(viewFamilyCallsignResponseDto);

		}
		return list;
	}

	@Override
	public void updateFamily(UpdateFamilyRequestDto updateFamilyRequestDto, MultipartFile file) {

		UpdateFamilyDto updateFamilyDto = updateFamilyRequestDto.getUpdatefamilyDto();
		Family family = familyRepository.findById(updateFamilyDto.getFamilyId()).get();

		family.updateFamilyName(updateFamilyDto.getFamilyName());

		// ?????? ????????? ?????????
		if (file != null) {

			// ?????? ??????
			File uploadPathFolder = new File(uploadPath);
			if (!uploadPathFolder.exists())
				uploadPathFolder.mkdirs();

			// ?????? ?????? ????????? ??????
			String existingPath = family.getFamilyImagePath();
			if (existingPath != null) {
				File existingImage = new File(existingPath);
				if (existingImage.exists())
					existingImage.delete();
			}

			// ????????? ????????? ??????
			String fileType = file.getContentType();
			String OriginalName = file.getOriginalFilename();
			String fileName = OriginalName.substring(OriginalName.lastIndexOf('\\') + 1);
			String saveName = UUID.randomUUID().toString() + "_" + fileName;
			String thumbnailPath = uploadPath + File.separator + saveName;
			String frontThumbnailPath = frontPath + File.separator + saveName;

			try {

				InputStream in = file.getInputStream();
				BufferedImage originalImage = ImageIO.read(in);

				File thumbnailFile = new File(thumbnailPath);
				Thumbnails.of(originalImage).size(500, 500).crop(Positions.CENTER).toFile(thumbnailFile);

			} catch (IOException e) {
				e.printStackTrace();
			}

			family.updateFamilyImage(OriginalName, frontThumbnailPath, thumbnailPath, fileType);
		}

		familyRepository.save(family);

		// ????????? ??????
		if (updateFamilyRequestDto.getIsCallsignModified()) {

			List<UpdateFamilyCallsignDto> updateFamilyCallsignDtos = updateFamilyRequestDto
					.getUpdateFamilyCallsignDtos();

			for (UpdateFamilyCallsignDto updateFamilyCallsignDto : updateFamilyCallsignDtos) {
				FamilyCallsign familyCallsign = familyCallsignRepository
						.findById(updateFamilyCallsignDto.getFamilyCallsignId()).get();
				familyCallsign.updateCallsign(updateFamilyCallsignDto.getCallsign());
				familyCallsignRepository.save(familyCallsign);
			}
		}
	}

	private static String createRandomFamilyId() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 57; // letter '9'
//		int rightLimit = 90; // letter 'Z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}
}
