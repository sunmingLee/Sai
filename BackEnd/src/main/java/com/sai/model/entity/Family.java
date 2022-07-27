package com.sai.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

	@Column(name = "family_image_name")
	private String familyImageName;

	@Column(name = "family_image_type")
	private String familyImageType;

}
