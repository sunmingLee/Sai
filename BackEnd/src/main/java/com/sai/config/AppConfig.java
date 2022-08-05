package com.sai.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sai.model.dto.board.InputBoardRequestDto;
import com.sai.model.entity.Board;

@Configuration
public class AppConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(true) // null 에러 방지
				.setSkipNullEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE) // setter없이 사용하기 위한 설정
				.setAmbiguityIgnored(true) // entityId matches multiple source property hierarchies: 에러 방지
//				.setMatchingStrategy(MatchingStrategies.STRICT)
		;

//		modelMapper.typeMap(InputBoardRequestDto.class, Board.class).addMapping(mapper ->{
//			mapper.skip(Board::setBoardId);
//		});

//		modelMapper.addMappings(userMap);

		return modelMapper;
	}

//	PropertyMap<User, UserDto> userMap = new PropertyMap<User, UserDto>() { // 커스터마이징
//
//		@Override
//		protected void configure() {
//			map().setFamilyId(source.getFamily().getFamilyId());
//		}
//	};
}
