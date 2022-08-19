package com.sai.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.Family;
import com.sai.model.entity.Memo;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

	public List<Memo> findMemosByFamilyOrderByMemoRegDateTimeDesc(Family family);

	public List<Memo> findAllByFamilyAndMemoRegDateTimeBetweenOrderByMemoRegDateTimeDesc(Family family, LocalDateTime startDatetime,
			LocalDateTime endDatetime);

//	public List<Memo> findAllByFamilyAndMemoRegDateTimeBetween(Family family, LocalDateTime startDatetime,
//			LocalDateTime endDatetime);

}
