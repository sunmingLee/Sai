package com.sai.model.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.model.repository.FamilyRepository;
import com.sai.model.repository.UserRepository;

@Service
@Transactional
public class FeedServiceImpl implements FeedService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	ModelMapper modelMapper;
}
