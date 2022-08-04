package com.sai.model.service.user;

import java.util.HashMap;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sai.dto.MailDto;
import com.sai.dto.user.InfoUserResponseDto;
import com.sai.dto.user.LoginUserRequestDto;
import com.sai.dto.user.LoginUserResponseDto;
import com.sai.dto.user.UserDto;
import com.sai.jwt.JwtTokenProvider;
import com.sai.model.entity.family.FamilyRegister;
import com.sai.model.entity.user.User;
import com.sai.model.repository.family.FamilyRegisterRepository;
import com.sai.model.repository.user.UserRepository;
import com.sai.model.service.MailService;
import com.sai.poll.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final FamilyRegisterRepository familyRegisterRepository;
	private final MailService mailService;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	ModelMapper modelMapper = new ModelMapper();

	// 아이디 중복 체크
	@Override
	public boolean checkUserIdDuplicate(String userId) {
		return userRepository.existsByUserId(userId);
	}

	// 이메일 중복 체크
	@Override
	public boolean checkUserEmailDuplicate(String email) {
		return userRepository.existsByEmail(email);
	}

	// 직접 회원 가입
	@Override
	public String insertUser(UserDto userInfo) {
		String userId = userInfo.getUserId();
		String email = userInfo.getEmail();
		String password = userInfo.getPassword();
		String userName = userInfo.getUserName();

		User user = User.builder().userId(userId).email(email).password(passwordEncoder.encode(password))
				.userName(userName).build();
		userRepository.save(user);
		return "회원가입 성공";
	}

	// 사용자 정보 조회
	@Override
	public UserDto getUserInfoByUserId(String userId) {

		User user = userRepository.findById(userId).get();
		UserDto userDto = modelMapper.map(user, UserDto.class);

		return userDto;
	}

	// 비밀번호 수정
	@Override
	public String changePassword(String userId, String password) {
		User user = userRepository.findById(userId).get();
		user.updateUserPassword(password);
		userRepository.save(user);
		return "비밀번호 수정";
	}

	// 회원 탈퇴
	@Override
	public String deleteUser(String userId) {
		User user = userRepository.findById(userId).get();
		userRepository.delete(user);
		return "회원 탈퇴";
	}

	// 로그인
	@Override
	public String login(LoginUserRequestDto user) {
//		LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
		User loginUser = userRepository.findByUserId(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", user.getUserId()));
		if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
//			loginUserResponseDto.setJWT(jwtTokenProvider.createToken(loginUser.getUserId()));
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		return jwtTokenProvider.createToken(user.getUserId());
	}
	
	// 로그인 인증 후 가족 정보 찾기
	public InfoUserResponseDto loginUserInfo(LoginUserRequestDto loginUserRequestDto) {
		InfoUserResponseDto infoUserResponseDto = new InfoUserResponseDto();
		infoUserResponseDto.setUserId(loginUserRequestDto.getUserId());
		
		User loginUser = userRepository.findByUserId(loginUserRequestDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", loginUserRequestDto.getUserId()));
				
		if(loginUser.getFamilyId() == null) {
			FamilyRegister familyRegister = familyRegisterRepository.findOneByUserUserId(loginUserRequestDto.getUserId());
			// 
			if(familyRegister == null) {
				return infoUserResponseDto;
			}
			if(!familyRegister.getApproveYn()) {
				infoUserResponseDto.setFamilyReg(false);
			} else if (familyRegister.getApproveYn()) {
				infoUserResponseDto.setFamilyReg(true);
			}
			
		} else {
			infoUserResponseDto.setFamilyId(loginUser.getFamilyId().getFamilyId());
		}
		
		return infoUserResponseDto;
	}

	// 아이디 찾기
	@Override
	public HashMap<String, Object> findUserId(UserDto user) {
		HashMap<String, Object> result = new HashMap<>();

		// 이름으로 해당 유저 찾아오기
		Optional<User> findUser = userRepository.findByUserName(user.getUserName());

		findUser.ifPresentOrElse(foundUser -> {
			// 이름에 해당하는 이메일이 맞는지 확인
			if (foundUser.getEmail().equals(user.getEmail())) {

				// 메일 생성 & 전송
				MailDto mail = mailService.createMail(foundUser.getUserId(), foundUser.getEmail(), "findUserId");
				mailService.sendMail(mail);

				result.put("msg", "입력하신 이메일로 아이디가 전송되었습니다.");
				result.put("status", HttpStatus.ACCEPTED);

				// 이메일이 틀린 경우
			} else {
				result.put("msg", "입력하신 정보가 일치하지 않습니다.");
				result.put("status", HttpStatus.ACCEPTED);
			}
			// 이름이 틀린 경우
		}, () -> {
			result.put("msg", "입력하신 정보가 일치하지 않습니다.");
			result.put("status", HttpStatus.ACCEPTED);
		});

		return result;
	}

	// 비밀번호 찾기
	@Override
	public HashMap<String, Object> findUserPw(UserDto user) {
		HashMap<String, Object> result = new HashMap<>();

		// 유저 이름으로 옵셔널 불러옴
		Optional<User> findUser = userRepository.findByUserName(user.getUserName());

		findUser.ifPresentOrElse(foundUser -> {
			// 이름에 해당하는 이메일이 맞는지 확인
			if (foundUser.getEmail().equals(user.getEmail())) {

				// 아이디도 맞는지 확인
				if (foundUser.getUserId().equals(user.getUserId())) {

					// 비밀번호 재설정
					char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
							'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
							'W', 'X', 'Y', 'Z', '#', '`', '?', '!', '@', '$', '%', '^', '&', '*', '-' };

					String pwd = "";

					// 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합
					int idx = 0;
					for (int i = 0; i < 10; i++) {
						idx = (int) (charSet.length * Math.random());
						pwd += charSet[idx];
					}

					// 임시 비밀번호 저장
					foundUser.updateUserPassword(passwordEncoder.encode(pwd));
					userRepository.save(foundUser);

					// 메일 생성 & 전송
					MailDto mail = mailService.createMail(pwd, foundUser.getEmail(), "findUserPw");
					mailService.sendMail(mail);

					//
					result.put("msg", "입력하신 이메일로 임시 비밀번호가 전송되었습니다.");
					result.put("status", HttpStatus.ACCEPTED);

				}
				// 이메일은 맞고 아이디는 틀린 경우
				else {
					result.put("msg", "입력하신 정보가 일치하지 않습니다.");
					result.put("status", HttpStatus.ACCEPTED);
				}

			} else {
				result.put("msg", "입력하신 정보가 일치하지 않습니다.");
				result.put("status", HttpStatus.ACCEPTED);
			}
		}, () -> {
			result.put("msg", "입력하신 정보가 일치하지 않습니다.");
			result.put("status", HttpStatus.ACCEPTED);
		});

		return result;
	}

}
