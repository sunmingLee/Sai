package com.sai.model.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Family {

	@Id
	@Column(name = "family_id")
	private String familyId;

	@Column(name = "family_name")
	private String familyName;

	@Column(name = "family_image_path")
	private String familyImagePath;

	@Column(name = "family_image_path_server")
	private String familyImagePathServer;

	@Column(name = "family_image_name")
	private String familyImageName;

	@Column(name = "family_image_type")
	private String familyImageType;

	@Builder.Default
	@OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<User> users = new ArrayList<User>();

	public void updateFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public void addUser(User user) {
		this.users.add(user);
		if (user.getFamily() != this)
			user.setFamily(this);
	}

	public void updateFamilyImage(String familyImageName, String familyImagePath, String familyImagePathServer,
			String familyImageType) {
		this.familyImageName = familyImageName;
		this.familyImagePath = familyImagePath;
		this.familyImagePathServer = familyImagePathServer;
		this.familyImageType = familyImageType;
	}


	public boolean deleteImage() {
		File file = new File(familyImagePathServer);
		return file.delete();
	}

}
