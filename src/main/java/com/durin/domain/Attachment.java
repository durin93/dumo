package com.durin.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Attachment extends AbstractEntity {

	@Transient
	private static final String PROFILE_TYPE = "profile";
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_writer"))
	private User writer;

	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	@JsonProperty
	private String originalFileName;

	@Column(nullable = false)
	private String saveFileName;

	@Column(nullable = false)
	private String path;

	public Attachment() {
	}

	public Attachment(User loginUser, String originalName, String uploadPath, String type)  {
		super(0L); //이걸해주어야Getter for property 'id' threw exception 해결
		this.writer = loginUser;
		this.originalFileName = originalName;
		this.saveFileName = UUID.randomUUID() + "_" + originalName;
		this.path = uploadPath;
		this.type = type;
	}
	
	public static Attachment ofProfile(User loginUser, String originalName, String uploadPath) {
		return new Attachment(loginUser, originalName, uploadPath, PROFILE_TYPE);
	}

	public void update(String originalFilename) {
		this.originalFileName = originalFilename;
		this.saveFileName = UUID.randomUUID() + "_" + originalFilename;
	}

	public User getWriter() {
		return writer;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public String getPath() {
		return path;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((originalFileName == null) ? 0 : originalFileName.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((saveFileName == null) ? 0 : saveFileName.hashCode());
		result = prime * result + ((writer == null) ? 0 : writer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attachment other = (Attachment) obj;
		if (originalFileName == null) {
			if (other.originalFileName != null)
				return false;
		} else if (!originalFileName.equals(other.originalFileName))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (saveFileName == null) {
			if (other.saveFileName != null)
				return false;
		} else if (!saveFileName.equals(other.saveFileName))
			return false;
		if (writer == null) {
			if (other.writer != null)
				return false;
		} else if (!writer.equals(other.writer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attachment [writer=" + writer + ", originalFileName=" + originalFileName + ", saveFileName="
				+ saveFileName + ", path=" + path + "]";
	}


}
