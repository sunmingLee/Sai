package com.sai.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.album.InputAlbumRequestDto;
import com.sai.model.dto.album.ViewAlbumResponseDto;
import com.sai.model.dto.albumMedia.DownloadAlbumMediaDto;
import com.sai.model.dto.albumMedia.ViewAlbumMediaResponseDto;
import com.sai.model.entity.Album;
import com.sai.model.entity.AlbumMedia;
import com.sai.model.entity.Family;
import com.sai.model.repository.AlbumMediaRepository;
import com.sai.model.repository.AlbumRepository;
import com.sai.model.repository.FamilyRepository;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

	private String uploadPath = File.separator + "app" + File.separator + "Album";
	private String frontPath = File.separator + "saimedia" + File.separator + "Album";

	@Autowired
	AlbumRepository albumRepository;

	@Autowired
	AlbumMediaRepository albumMediaRepository;

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public void makeAlbum(InputAlbumRequestDto inputAlbumRequestDto) {
		Album album = modelMapper.map(inputAlbumRequestDto, Album.class);
		albumRepository.save(album);
	}

	@Override
	public List<ViewAlbumResponseDto> getAlbumList(String familyId) {
		List<ViewAlbumResponseDto> viewAlbumResponseDtos = new ArrayList<>();

		Family family = familyRepository.findById(familyId).get();
		List<Album> albums = albumRepository.findByFamily(family);
		for (Album album : albums) {

			ViewAlbumResponseDto viewAlbumResponseDto = modelMapper.map(album, ViewAlbumResponseDto.class);
			AlbumMedia albumMedia = albumMediaRepository.findFirstByAlbumOrderByAlbumMediaIdDesc(album);
			if (albumMedia != null)
				viewAlbumResponseDto.setThumbnailPath(albumMedia.getAlbumMediaThumbnail());
			;

			viewAlbumResponseDtos.add(viewAlbumResponseDto);
		}

		return viewAlbumResponseDtos;
	}

	@Override
	public List<ViewAlbumMediaResponseDto> getAlbumMediaList(Long albumId, Pageable pageable) {
		List<ViewAlbumMediaResponseDto> viewAlbumMediaResponseDtos = new ArrayList<>();
		Album album = albumRepository.findById(albumId).get();
		List<AlbumMedia> albumMedias = albumMediaRepository.findByAlbum(album, pageable);
		for (AlbumMedia albumMedia : albumMedias) {
			viewAlbumMediaResponseDtos.add(modelMapper.map(albumMedia, ViewAlbumMediaResponseDto.class));
		}

		return viewAlbumMediaResponseDtos;
	}

	@Override
	public void deleteAlbum(Long albumId) {
		Album album = albumRepository.findById(albumId).get();
		List<AlbumMedia> albumMedias = albumMediaRepository.findByAlbum(album);
		for (AlbumMedia albumMedia : albumMedias) {
			albumMedia.delete();
		}

		albumRepository.delete(album);
	}

	@Override
	public void insertAlbumMedia(Long albumId, List<MultipartFile> files) {
		System.out.println(uploadPath);
		Album album = albumRepository.findById(albumId).get();

		if (files != null && files.size() > 0) {

			// 가족아이디로 폴더만들기
			String folderPath = makeFolder(album.getFamily().getFamilyId());

			for (MultipartFile file : files) {
				if (file.isEmpty())
					continue;

				String fileType = file.getContentType();
				String OriginalName = file.getOriginalFilename();
				String fileName = OriginalName.substring(OriginalName.lastIndexOf('\\') + 1);
				String saveName = UUID.randomUUID().toString() + "_" + fileName;
				String savePath = uploadPath + File.separator + folderPath + File.separator + saveName;
				String thumbnailPath = uploadPath + File.separator + folderPath + File.separator + "th_" + saveName;
				String frontSavePath = frontPath + File.separator + folderPath + File.separator + saveName;
				String frontThumbnailPath = frontPath + File.separator + folderPath + File.separator + "th_" + saveName;

				try {

					Path path = Paths.get(savePath);
					file.transferTo(path);
					File thumbnailFile = new File(thumbnailPath);
					Thumbnails.of(path.toFile()).size(400, 400).crop(Positions.CENTER).toFile(thumbnailFile);

				} catch (IOException e) {
					e.printStackTrace();
				}

				AlbumMedia albumMedia = AlbumMedia.builder().album(album).albumMediaPath(frontSavePath)
						.albumMediaPathServer(savePath).albumMediaOriginalName(OriginalName).albumMediaType(fileType)
						.albumMediaThumbnail(frontThumbnailPath).albumMediaThumbServer(thumbnailPath).build();
				albumMediaRepository.save(albumMedia);

			}
		}
	}

	@Override
	public DownloadAlbumMediaDto downloadAlbumMedia(Long albumMediaId) {

		AlbumMedia albumMedia = albumMediaRepository.findById(albumMediaId).get();
		Path path = Paths.get(albumMedia.getAlbumMediaPath());
//		String contentType = albumMedia.getAlbumMediaType();
		try {
			String contentType = Files.probeContentType(path);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment")
					.filename(albumMedia.getAlbumMediaOriginalName(), StandardCharsets.UTF_8).build());
			headers.add(contentType, contentType);

			Resource resource = new InputStreamResource(Files.newInputStream(path));

			DownloadAlbumMediaDto downloadAlbumMediaDto = DownloadAlbumMediaDto.builder().headers(headers)
					.resource(resource).build();
			return downloadAlbumMediaDto;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void deleteAlbumMedia(Long albumMediaId) {
		AlbumMedia albumMedia = albumMediaRepository.findById(albumMediaId).get();
		albumMedia.delete();
		albumMediaRepository.delete(albumMedia);
	}

	private String makeFolder(String familyId) {
		File uploadPathFolder = new File(uploadPath, familyId);
		if (!uploadPathFolder.exists())
			uploadPathFolder.mkdirs();

		return familyId;
	}
}
