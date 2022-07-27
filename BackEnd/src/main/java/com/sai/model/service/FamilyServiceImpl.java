package com.sai.model.service;

import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.model.entity.Family;
import com.sai.model.entity.User;
import com.sai.model.repository.FamilyRepository;
import com.sai.model.repository.UserRepository;

@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public User createFamilyId(String userId) {
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

		return user;
	}

	@Override
	public User disjoinFamily(String userId) {
		User user = userRepository.findById(userId).get();
		Family family = user.getFamily();
		user.setFamily(null);
		userRepository.save(user);
		if (family.getUsers().size() == 0)
			familyRepository.delete(family);

		return user;
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
