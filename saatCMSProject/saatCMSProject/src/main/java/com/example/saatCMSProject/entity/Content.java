package com.example.saatCMSProject.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.saatCMSProject.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "content")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Content  {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id

	@Column(name = "content_id")
	private long id;


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	  joinColumns = @JoinColumn(name = "content_id"), 
	  inverseJoinColumns = @JoinColumn(name = "license_id"))
	@Nullable
	private List<License> licenses = new ArrayList<License>();

	@Column(name = "name")
	@Nullable
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@Nullable
	private Status status = Status.InProgress;

	@Column(name = "poster_url")
	@Nullable
	private String posterUrl;

	@Column(name = "video_url")
	@Nullable
	private String videoUrl;
}
