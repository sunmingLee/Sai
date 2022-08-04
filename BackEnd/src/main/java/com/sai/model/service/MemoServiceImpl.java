package com.sai.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.model.dto.memo.CreateMemoRequestDto;
import com.sai.model.dto.memo.MemoDto;
import com.sai.model.dto.memo.MemoPopupDto;
import com.sai.model.entity.Family;
import com.sai.model.entity.Memo;
import com.sai.model.entity.User;
import com.sai.model.repository.FamilyRepository;
import com.sai.model.repository.MemoRepository;
import com.sai.model.repository.UserRepository;

@Service
public class MemoServiceImpl implements MemoService {

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	MemoRepository memoRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<MemoPopupDto> getMemoPopup(String userId, String familyId) {

		// familyId에 해당하는 Memo를 찾기 위해 Family 객체를 가져옵니다.
		Family family = familyRepository.findById(familyId).get();

		// 리턴해줄 MemoPopup 리스트스를 생성합니다.
		List<MemoPopupDto> resultList = new ArrayList<MemoPopupDto>();

		// 위에서 찾은 Family 객체를 이용하여 우리 가족의 메모 리스트를 모두 불러옵니다.
		List<Memo> memoList = memoRepository.findMemosByFamily(family);

		// 우리 가족의 메모 리스트 중 아직 내가 확인하지 않은 메모를 MemoPopup 리스트에 담습니다.
		for (Memo memo : memoList) {

			// 팝업에 들어가야할 메모인지 체크하는 플래그입니다.
			boolean flag = true;

			// 해당 컬럼이 not null 속성이 아니어서 Null Pointer Exception을 방지합니다.
			String readList = memo.getReadList();
			if (readList == null)
				break;

			// 받아온 읽은 사람 목록 문자열에서 내 이름이 있는지 체크합니다.
			StringTokenizer st = new StringTokenizer(memo.getReadList(), "+");
			while (st.hasMoreTokens()) {
				if (st.nextToken().equals(userId)) {
					// 읽은 사람 목록 문자열에 내 이름이 있다면 팝업 리스트에 담지 않습니다.
					flag = false;
					break;
				}
			}

			// 팝업에 들어가야할 메모라면, Entity를 DTO로 변환하여 담아줍니다.
			if (flag) {
				MemoPopupDto item = modelMapper.map(memo, MemoPopupDto.class);
				resultList.add(item);
			}
		}

		// 결과로 팝업 리스트를 반환합니다.
		return resultList;
	}

	@Override
	public List<MemoDto> getMemo(String familyId) {
		// familyId에 해당하는 Memo를 찾기 위해 Family 객체를 가져옵니다.
		Family family = familyRepository.findById(familyId).get();

		// 리턴해줄 Memo 리스트스를 생성합니다.
		List<MemoDto> resultList = new ArrayList<MemoDto>();

		// 위에서 찾은 Family 객체를 이용하여 우리 가족의 메모 리스트를 모두 불러옵니다.
		List<Memo> memoList = memoRepository.findMemosByFamily(family);

		// 우리 가족의 메모를 Memo 리스트에 담습니다.
		for (Memo memo : memoList) {
			MemoDto item = modelMapper.map(memo, MemoDto.class);
			resultList.add(item);
		}
		// 결과로 메모 리스트를 반환합니다.
		return resultList;
	}

	@Override
	public void readMemo(String userId) {
		Family family = userRepository.findById(userId).get().getFamily();
		List<Memo> memoList = memoRepository.findMemosByFamily(family);
		for (Memo memo : memoList) {
			// 현재 메모의 읽음 목록에 내 아이디가 있는지 확인하는 플래그입니다.
			boolean flag = false;

			// 해당 컬럼이 not null 속성이 아니어서 Null Pointer Exception을 방지합니다.
			String readList = memo.getReadList();
			if (readList == null)
				break;

			// 받아온 읽은 사람 목록 문자열에서 내 아이디가 있는지 체크합니다.
			StringTokenizer st = new StringTokenizer(memo.getReadList(), "+");
			while (st.hasMoreTokens()) {
				if (st.nextToken().equals(userId)) {
					flag = true;
					break;
				}
			}
			

			// 내 아이디가 읽음 목록에 없다면, 문자열에 내 아이디를 추가해줍니다.
			if (!flag) {
				StringBuilder sb = new StringBuilder();
				sb.append(memo.getReadList()).append("+" + userId);
				memo.updateReadList(sb.toString());
				memoRepository.save(memo);
			}
		}

	}

	@Override
	public void createMemo(CreateMemoRequestDto createMemoRequestDto) {
		User user = userRepository.findById(createMemoRequestDto.getUserId()).get();
		Family family = familyRepository.findById(user.getFamily().getFamilyId()).get();
		Memo memo = Memo.builder()
				.family(family)
				.user(user)
				.memoRegDateTime(LocalDateTime.now())
				.x(createMemoRequestDto.getX())
				.y(createMemoRequestDto.getY())
				.color(createMemoRequestDto.getColor())
				.memoContent(createMemoRequestDto.getMemoContent())
				.readList(createMemoRequestDto.getUserId())
				.build();
		memoRepository.save(memo);
	}

	@Override
	public void changeColor(Long memoId, String color) {
		Memo memo = memoRepository.findById(memoId).get();
		memo.updateColor(color);
		memoRepository.save(memo);
	}

	@Override
	public void deleteMemo(Long memoId) {
		Memo memo = memoRepository.findById(memoId).get();
		memoRepository.delete(memo);
	}

}
