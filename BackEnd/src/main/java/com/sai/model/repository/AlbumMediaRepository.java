package com.sai.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Album;
import com.sai.model.entity.AlbumMedia;

public interface AlbumMediaRepository extends JpaRepository<AlbumMedia, Long> {

	List<AlbumMedia> findByAlbum(Album album, Pageable pageable);

	List<AlbumMedia> findByAlbum(Album album);

	AlbumMedia findFirstByAlbumOrderByAlbumMediaIdDesc(Album album);
}
