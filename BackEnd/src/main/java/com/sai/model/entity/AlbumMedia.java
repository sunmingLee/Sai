package com.sai.model.entity;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "album_media")
public class AlbumMedia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "album_media_id", updatable = false, insertable = false)
	private Long albumMediaId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id")
	private Album album;

	@Column(name = "album_media_thumbnail")
	private String albumMediaThumbnail;

	@Column(name = "album_media_thumb_server")
	private String albumMediaThumbServer;

	@Column(name = "album_media_path")
	private String albumMediaPath;

	@Column(name = "album_media_path_server")
	private String albumMediaPathServer;

	@Column(name = "album_media_original_name")
	private String albumMediaOriginalName;

	@Column(name = "album_media_type")
	private String albumMediaType;

	public void setAlbum(Album album) {
		this.album = album;
	}

	public boolean delete() {
		File file = new File(albumMediaPathServer);
		File thumbnail = new File(albumMediaThumbServer);
		return file.delete() && thumbnail.delete();
	}
}
