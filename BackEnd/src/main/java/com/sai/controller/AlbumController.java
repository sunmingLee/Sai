package com.sai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.album.InputAlbumRequestDto;
import com.sai.model.dto.album.ViewAlbumResponseDto;
import com.sai.model.dto.albumMedia.DownloadAlbumMediaDto;
import com.sai.model.dto.albumMedia.ViewAlbumMediaResponseDto;
import com.sai.model.service.AlbumService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/album")
@CrossOrigin(origins = "*")
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@ApiOperation(value = "makeAlbum : 앨범 생성")
	@PostMapping("/")
	public ResponseEntity<?> makeAlbum(@RequestBody InputAlbumRequestDto inputAlbumRequestDto) throws Exception {

		try {
			albumService.makeAlbum(inputAlbumRequestDto);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "getAlbumList : 앨범 리스트 조회")
	@GetMapping("/family/{familyId}")
	public ResponseEntity<?> getAlbumList(@PathVariable String familyId) throws Exception {
		System.out.println("앨범 조회야");
		try {
			List<ViewAlbumResponseDto> viewAlbumListResponseDtos = albumService.getAlbumList(familyId);

			if (viewAlbumListResponseDtos != null) {
				return new ResponseEntity<List<ViewAlbumResponseDto>>(viewAlbumListResponseDtos, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "getAlbumMediaList : 앨범 상세 조회(미디어 리스트 조회)")
	@GetMapping("/{albumId}")
	public ResponseEntity<?> getAlbumMediaList(@PathVariable Long albumId,
			@PageableDefault(size = 18, sort = "albumMediaId", direction = Direction.DESC) Pageable pageable)
			throws Exception {

		try {
			List<ViewAlbumMediaResponseDto> viewAlbumMediaResponseDtos = albumService.getAlbumMediaList(albumId,
					pageable);

			if (viewAlbumMediaResponseDtos != null) {
				return new ResponseEntity<List<ViewAlbumMediaResponseDto>>(viewAlbumMediaResponseDtos, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "deleteAlbum : 앨범 삭제")
	@DeleteMapping("/{albumId}")
	public ResponseEntity<?> deleteAlbum(@PathVariable Long albumId) throws Exception {
		try {
			albumService.deleteAlbum(albumId);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "insertAlbumMedia : 앨범 미디어 등록")
	@PostMapping("/{albumId}")
	public ResponseEntity<?> insertAlbumMedia(@PathVariable Long albumId,
			@RequestPart(name = "files", required = false) List<MultipartFile> files) throws Exception {
		try {
			albumService.insertAlbumMedia(albumId, files);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "downloadAlbumMedia : 앨범 미디어 다운로드")
	@GetMapping("/media/{albumMediaId}")
	public ResponseEntity<?> downloadAlbumMedia(@PathVariable Long albumMediaId) throws Exception {
		try {
			DownloadAlbumMediaDto downloadAlbumMediaDto = albumService.downloadAlbumMedia(albumMediaId);
			return new ResponseEntity<Resource>(downloadAlbumMediaDto.getResource(), downloadAlbumMediaDto.getHeaders(),
					HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "deleteAlbumMedia : 앨범 미디어 삭제")
	@DeleteMapping("/media/{albumMediaId}")
	public ResponseEntity<?> deleteAlbumMedia(@PathVariable Long albumMediaId) throws Exception {
		try {
			albumService.deleteAlbumMedia(albumMediaId);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}