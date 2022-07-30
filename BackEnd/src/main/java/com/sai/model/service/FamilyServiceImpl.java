package com.sai.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.model.dto.family.FamilyCallsignDto;
import com.sai.model.dto.family.FamilyDto;
import com.sai.model.dto.family.FamilyRegisterDto;
import com.sai.model.dto.family.UpdateFamilyVo;
import com.sai.model.entity.Family;
import com.sai.model.entity.FamilyCallsign;
import com.sai.model.entity.FamilyRegister;
import com.sai.model.entity.User;
import com.sai.model.repository.FamilyCallsignRepository;
import com.sai.model.repository.FamilyRegisterRepository;
import com.sai.model.repository.FamilyRepository;
import com.sai.model.repository.UserRepository;

@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	FamilyRegisterRepository familyRegisterRepository;

	@Autowired
	FamilyCallsignRepository familyCallsignRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public FamilyDto createFamilyId(String userId) {
		String familyId;
		while (true) {
			familyId = createRandomFamilyId();
			if (!familyRepository.existsById(familyId))
				break;
		}

		Family family = Family.builder().familyId(familyId).familyName("우리 가족").build();
		User user = userRepository.findById(userId).get();
		family.addUser(user);
		userRepository.save(user);
		familyRepository.save(family);

		// 내 이름
		FamilyCallsign callsign = FamilyCallsign.builder().fromUser(user).toUser(user).callsign(user.getUserName())
				.build();
		familyCallsignRepository.save(callsign);

		return modelMapper.map(family, FamilyDto.class);
	}

	@Override
	public void disjoinFamily(String userId) {
		User user = userRepository.findById(userId).get();
		Family family = user.getFamily();

		// 콜사인 삭제
		familyCallsignRepository.removeByFromUser(user);
		familyCallsignRepository.removeByToUser(user);

		user.setFamily(null);
		userRepository.save(user);

		// 가족이 0명이면 가족 삭제
		if (family.getUsers().size() == 0)
			familyRepository.delete(family);
	}

	@Override
	public void applyFamily(FamilyRegisterDto familyRegisterDto) {
		FamilyRegister familyRegister = modelMapper.map(familyRegisterDto, FamilyRegister.class);

		familyRegisterRepository.save(modelMapper.map(familyRegisterDto, FamilyRegister.class));
	}

	@Override
	public void responseApplication(FamilyRegisterDto familyRegisterDto) {
		FamilyRegister findFamilyRegister = familyRegisterRepository.findById(familyRegisterDto.getFamilyRegisterId())
				.get();
		if (familyRegisterDto.getApproveYn()) { // 수락이면 가족에 추가, 가족 호칭 추가, 신청 레코드 삭제
			// 1. 유저 가족ID 변경

			Family family = findFamilyRegister.getFamily();
			User user = findFamilyRegister.getUser();
			family.addUser(user);
			userRepository.save(user);
			familyRepository.save(family);

			// 2. 가족 호칭 추가

			List<User> toUsers = family.getUsers();
			for (User toUser : toUsers) {
				if (user.equals(toUser))
					continue;
				// 내가 부르는 경우
				FamilyCallsign callsign1 = FamilyCallsign.builder().fromUser(user).toUser(toUser)
						.callsign(toUser.getUserName()).build();
				familyCallsignRepository.save(callsign1);
				// 나를 부르는 경우
				FamilyCallsign callsign2 = FamilyCallsign.builder().fromUser(toUser).toUser(user)
						.callsign(user.getUserName()).build();
				familyCallsignRepository.save(callsign2);
			}
			// 내 이름
			FamilyCallsign callsign3 = FamilyCallsign.builder().fromUser(user).toUser(user).callsign(user.getUserName())
					.build();
			familyCallsignRepository.save(callsign3);

			// 3. 신청 레코드 삭제
			familyRegisterRepository.delete(findFamilyRegister);

		} else { // 거절인 경우
			findFamilyRegister.updateResponse(familyRegisterDto.getApproveYn());
			familyRegisterRepository.save(findFamilyRegister);
		}
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
	public List<FamilyCallsignDto> searchFamilyList(String userId) {
		User user = userRepository.findById(userId).get();
		List<FamilyCallsign> familyCallsigns = familyCallsignRepository.findByFromUser(user);

		List<FamilyCallsignDto> list = new ArrayList<>();
		for (FamilyCallsign familyCallsign : familyCallsigns) {
			list.add(modelMapper.map(familyCallsign, FamilyCallsignDto.class));
		}
		return list;
	}

	@Override
	public UpdateFamilyVo updateFamily(UpdateFamilyVo updateFamilyDto) {
		// 가족 정보 변경
		FamilyDto familyDto = updateFamilyDto.getFamilyDto();
		Family family = familyRepository.findById(familyDto.getFamilyId()).get();

		System.out.println(familyDto.getFamilyName());
		family.updateFamily(modelMapper.map(familyDto, Family.class));
		System.out.println(modelMapper.map(familyDto, Family.class).getFamilyName());

		familyRepository.save(family);
		System.out.println(family.getFamilyName());

		// 콜사인 변경
		List<FamilyCallsignDto> familyCallsignDtos = updateFamilyDto.getFamilyCallsignDtos();
		for (FamilyCallsignDto familyCallsignDto : familyCallsignDtos) {
			FamilyCallsign familyCallsign = familyCallsignRepository.findById(familyCallsignDto.getFamilyCallsignId())
					.get();
			familyCallsign.updateCallsign(familyCallsignDto.getCallsign());
			familyCallsignRepository.save(familyCallsign);
		}

		return updateFamilyDto;
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

		System.out.println(generatedString);
		return generatedString;
	}
}
