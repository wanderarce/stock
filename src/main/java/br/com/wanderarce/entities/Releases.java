package br.com.wanderarce.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Releases {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long volume;
	
	private LocalDateTime created;
	
	private LocalDateTime modified;
	
	@ManyToOne(fetch = FetchType.LAZY, optional= false)
	@JoinColumn(name="type_id", nullable = false)
	private Types type;
	
	@ManyToOne(fetch = FetchType.LAZY, optional= false)
	@JoinColumn(name="section_id", nullable = false)
	private Sections section;
	
	private String user;

	private String movement;
	
	public Releases() {}

	public Releases(Long id, Long volume, LocalDateTime created, LocalDateTime modified, Types type, Sections section,
			String user, String movement) {
		super();
		this.id = id;
		this.volume = volume;
		this.created = created;
		this.modified = modified;
		this.type = type;
		this.section = section;
		this.user = user;
		this.movement = movement;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public Sections getSection() {
		return section;
	}

	public void setSection(Sections section) {
		this.section = section;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMovement() {
		return movement;
	}

	public void setMovement(String movement) {
		this.movement = movement;
	}
}
