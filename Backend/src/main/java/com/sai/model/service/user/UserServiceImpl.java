package com.sai.model.service.user;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sai.exception.poll.ResourceNotFoundException;
import com.sai.jwt.JwtTokenProvider;
import com.sai.model.dto.MailDto;
import com.sai.model.dto.user.InfoUserResponseDto;
import com.sai.model.dto.user.LoginUserRequestDto;
import com.sai.model.dto.user.UserDto;
import com.sai.model.dto.user.UserInfoDTO;
import com.sai.model.entity.FamilyRegister;
import com.sai.model.entity.User;
import com.sai.model.entity.UserRole;
import com.sai.model.repository.BoardRepository;
import com.sai.model.repository.FamilyRegisterRepository;
import com.sai.model.repository.UserRepository;
import com.sai.model.service.MailService;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private String uploadPath = File.separator + "app" + File.separator + "UserImage";
	private String frontPath = File.separator + "saimedia" + File.separator + "UserImage";

	private final UserRepository userRepository;
	private final FamilyRegisterRepository familyRegisterRepository;
	private final BoardRepository boardRepository;
	private final MailService mailService;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

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
		String role = userInfo.getRole();

		User user = User.builder().userId(userId).email(email).password(passwordEncoder.encode(password))
				.role(UserRole.USER).userName(userName).build();
		userRepository.save(user);
		return "회원가입 성공";
	}

	// 회원정보 추가 혹은 수정
	@Override
	public String addUserInfo(UserInfoDTO addInfo, MultipartFile file) throws Exception {
		System.out.println(addInfo);
		User user = userRepository.findByUserId(addInfo.getUserId()).get();
		user.addUserinfo(addInfo);
		System.out.println(user.toString());
//		userRepository.save(user);

		if (file == null) {
			System.out.println("파일이 읍따");
		}

		// 유저 이미지 업로드
//		if (!file.isEmpty() || file != null) {
		if (file != null) {
			System.out.println("나야");
			// 폴더 생성
			File uploadPathFolder = new File(uploadPath);
			if (!uploadPathFolder.exists()) {
				uploadPathFolder.mkdirs();
			}
			// 이미 있는 이미지 삭제
			String existingPath = user.getUserImagePath();
			if (existingPath != null) {
				File existingImage = new File(existingPath);
				if (existingImage.exists()) {
					existingImage.delete();
				}
			}
			// 새로운 이미지 생성
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
				System.out.println("너니?");
				e.printStackTrace();
			}
			System.out.println("나거든");
			user.updateUserImage(OriginalName, frontThumbnailPath, thumbnailPath, fileType);
		}

		System.out.println("test3");
		userRepository.save(user);
		System.out.println("test2");
		return "유저 정보 추가 성공";
	}

	// 사용자 정보 조회
	@Override
	public UserDto getUserInfoByUserId(String userId) {

		User user = userRepository.findById(userId).get();
		UserDto userDto = modelMapper.map(user, UserDto.class);
		userDto.setPassword(null);
		return userDto;
	}

	// 개인정보 조회 전 유저 검증
	public void verifyUser(String userId, String password) {
		User loginUser = userRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		if (!passwordEncoder.matches(password, loginUser.getPassword())) {
//			loginUserResponseDto.setJWT(jwtTokenProvider.createToken(loginUser.getUserId()));
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
	}

	// 비밀번호 수정
	@Override
	public String changePassword(String userId, String password) {
		User user = userRepository.findById(userId).get();
		user.updateUserPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		return "비밀번호 수정";
	}

	// 회원 탈퇴
	@Override
	public String deleteUser(String userId) {
		User user = userRepository.findById(userId).get();
		user.deleteImage();
		userRepository.delete(user);
		return "회원 탈퇴";
	}

	// 로그인
	@Override
	public String login(LoginUserRequestDto user) {
//		LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
		User loginUser = userRepository.findByUserId(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", user.getUserId()));
		if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
//			loginUserResponseDto.setJWT(jwtTokenProvider.createToken(loginUser.getUserId()));
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		return jwtTokenProvider.createToken(user.getUserId());
	}

	// 로그인 인증 후 가족 정보 찾기
	// 로그인 인증 후 토큰 보내기 -> 토큰 쪼갠 후 유저 아이디 받아오기 -> 유저 검증 -> 유저 정보 중 가족 아이디 찾기
	// 가족 아이디 있다면 가족 아이디 & 가족 신청 여부는 true 반환 -> 피드 페이지
	// 가족 아이디 없다면 유저 아이디로 가족 신청 테이블 조회
	// 가족 신청이 없다면 가족 아이디는 null & 가족 신청 여부는 null 반환 -> 가족 신청 페이지
	// 가족 신청이 있다면
	// 가족 수락이 안된 경우 가족 아이디는 null & 가족 신청 여부는 true 반환 -> 가족 신청 후 대기 페이지
	public InfoUserResponseDto loginUserInfo(LoginUserRequestDto loginUserRequestDto) {
		InfoUserResponseDto infoUserResponseDto = new InfoUserResponseDto();
		infoUserResponseDto.setUserId(loginUserRequestDto.getUserId());

		User loginUser = userRepository.findByUserId(loginUserRequestDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", loginUserRequestDto.getUserId(),
						loginUserRequestDto.getUserId()));

		if (loginUser.getFamily() == null) {
			FamilyRegister familyRegister = familyRegisterRepository
					.findOneByUserUserId(loginUserRequestDto.getUserId());
//					.orElse();
//					.orElseThrow(() -> new ResourceNotFoundException("FamilyRegister", "familyRegister", loginUserRequestDto.getUserId()));

			// 가족 신청하지 않은 경우
			if (familyRegister == null) {
				return infoUserResponseDto; // user name 제외하고 모두 null로 리턴
			} else if (familyRegister.getApproveYn() == null) { // 아직 대기중인 경우
				infoUserResponseDto.setFamilyRegYN(true);
			}
			// 가족 신청한 경우
			else if (!familyRegister.getApproveYn()) { // 수락 거절된 경우
				infoUserResponseDto.setFamilyRegYN(true);
				infoUserResponseDto.setApprovedYN(false);
			}

		} else {
			infoUserResponseDto.setFamilyId(loginUser.getFamily().getFamilyId());
		}

		return infoUserResponseDto;
	}

	// 아이디 찾기
	@Override
	public HashMap<String, Object> findUserId(UserDto user) {
		HashMap<String, Object> result = new HashMap<>();

		// 이름으로 해당 유저 찾아오기
		Optional<User> findUser = userRepository.findByUserName(user.getUserName());

		findUser.ifPresent(foundUser -> {
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
		});

		return result;
	}

	// 비밀번호 찾기
	@Override
	public HashMap<String, Object> findUserPw(UserDto user) {
		HashMap<String, Object> result = new HashMap<>();

		// 유저 이름으로 옵셔널 불러옴
		Optional<User> findUser = userRepository.findByUserName(user.getUserName());

		findUser.ifPresent(foundUser -> {
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
		});

		return result;
	}

}
