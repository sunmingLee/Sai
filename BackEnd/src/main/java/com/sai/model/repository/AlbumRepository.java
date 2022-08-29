package com.sai.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Album;
import com.sai.model.entity.Family;

public interface AlbumRepository extends JpaRepository<Album, Long> {

	List<Album> findByFamily(Family family);
}
