package com.prodyna.ted.survey.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Version;

/**
 * Entity with basic properties.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private Long id;
	
	@Version
	@Column(nullable=false)
	private Long version;

	@Column(name = "CREATED")
	private Date created;

	@Column(name = "MODIFIED")
	private Date modified;
	

	@PostPersist
	public void postPersist() {

		this.created = new Date();
		this.modified = new Date();
	}

	@PostUpdate
	public void postUpdate() {

		this.modified = new Date();
	}
	
	public Long getId() {
	
		return id;
	}

	
	public void setId(Long id) {
	
		this.id = id;
	}

	
	public Long getVersion() {
	
		return version;
	}

	
	public void setVersion(Long version) {
	
		this.version = version;
	}
	
	public Date getCreated() {
		
		return created;
	}

	
	public void setCreated(Date created) {
	
		this.created = created;
	}

	
	public Date getModified() {
	
		return modified;
	}

	
	public void setModified(Date modified) {
	
		this.modified = modified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
}
