package com.sai.model.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sai.dto.Mail;
import com.sai.model.entity.User;
import com.sai.model.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.Return;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final MailService mailService;

	// 로그인
	public HashMap<String, Object> login(User user) {
		Optional<User> findUser = userRepository.findById(user.getUser_id());
		HashMap<String, Object> result = new HashMap<>();

		findUser.ifPresentOrElse(loginUser -> {
			if (loginUser.getPassword().equals(user.getPassword())) {
				result.put("msg", "로그인되었습니다.");
				result.put("status", HttpStatus.ACCEPTED);
			} else {
				result.put("msg", "비밀번호가 틀렸습니다.");
				result.put("status", HttpStatus.ACCEPTED);
			}
			;
		}, () -> {
			result.put("msg", "아이디가 존재하지 않습니다.");
			result.put("status", HttpStatus.ACCEPTED);
		});

		return result;
	}

	// 아이디 찾기
	public HashMap<String, Object> findUserId(User user) {
		HashMap<String, Object> result = new HashMap<>();

		// 유저 이름으로 옵셔널 불러옴
		Optional<User> findUser = userRepository.findByEmail(user.getEmail());
		
		findUser.ifPresentOrElse(foundUser -> {
			// 이름에 해당하는 이메일이 맞는지 확인
			if(foundUser.getEmail().equals(user.getEmail())) {
				// 메일 생성 & 전송
				Mail mail = mailService.createMail(foundUser.getUser_id(), foundUser.getEmail());
				mailService.sendMail(mail);
				
				//
				result.put("msg", "입력하신 이메일로 아이디가 전송되었습니다.");
				result.put("status", HttpStatus.ACCEPTED);
				
			} else {
				result.put("msg", "이메일이 틀렸습니다.");
				result.put("status", HttpStatus.ACCEPTED);
			}	
		}, () -> {
			result.put("msg", "사용자가 존재하지 않습니다.");
			result.put("status", HttpStatus.ACCEPTED);
		});
		
		return result;
	}

//	// 임시 비밀번호 생성
//	public String getTmpPassword() {
//		char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
//                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','#','`','?','!','@','$','%','^','&','*','-'};
//
//        String pwd = "";
//
//        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 
//        int idx = 0;
//        for(int i = 0; i < 10; i++){
//            idx = (int) (charSet.length * Math.random());
//            pwd += charSet[idx];
//        }
//        return pwd;
//	}
//	
//	
//    /** 임시 비밀번호로 업데이트 **/
//    public void updatePassword(String tmpPassword, String memberEmail) {
//
//        User user = userRepository.findByEmail(memberEmail).orElseThrow(() ->
//                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
//
//    }
	

	
	
	// 비밀번호 찾기
	public HashMap<String, Object> findUserPw(User user) {
		HashMap<String, Object> result = new HashMap<>();

		// 유저 이름으로 옵셔널 불러옴
		Optional<User> findUser = userRepository.findByEmail(user.getEmail());
		
		findUser.ifPresentOrElse(foundUser -> {
			// 이름에 해당하는 이메일이 맞는지 확인
			if(foundUser.getEmail().equals(user.getEmail())) {
				
				// 비밀번호 재설정
				char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
		                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','#','`','?','!','@','$','%','^','&','*','-'};

		        String pwd = "";

		        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 
		        int idx = 0;
		        for(int i = 0; i < 10; i++){
		            idx = (int) (charSet.length * Math.random());
		            pwd += charSet[idx];
		        }
		        
		        System.out.println(pwd);
		        
		        // 임시 비밀번호 저장
		        foundUser.setPassword(pwd);
				
				
				// 메일 생성 & 전송
				Mail mail = mailService.createMail(foundUser.getUser_id(), foundUser.getEmail());
				mailService.sendMail(mail);
				
				//
				result.put("msg", "입력하신 이메일로 아이디가 전송되었습니다.");
				result.put("status", HttpStatus.ACCEPTED);
				
			} else {
				result.put("msg", "이메일이 틀렸습니다.");
				result.put("status", HttpStatus.ACCEPTED);
			}	
		}, () -> {
			result.put("msg", "사용자가 존재하지 않습니다.");
			result.put("status", HttpStatus.ACCEPTED);
		});
		
		return result;
	}
	
	

}
