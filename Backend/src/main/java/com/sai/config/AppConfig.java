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
		;

		return modelMapper;
	}
}
