package com.bridgeit.toDoNotes.model;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="UserNotes")
public class Notes {

	private long noteId;
	private String title;
	private String content;
	private Date createdTime;
	private Date updatedTime;
	private User user ;
	private boolean isTrash;
	private boolean isArchive;
	private boolean isPinned;
	private String color;
	private Date reminder;
	private List<User> sharedUser;
	private String image;
	
	private Set<Label> labels = new HashSet<Label>();
	public Notes() {}
	
	public Notes(long  noteId,String title,String content, User user){
		this.noteId=noteId;
		this.title=title;
		this.content=content;
		this.user=user;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NoteId")
	public long getNoteId() {
		return noteId;
	}
	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	@Column(name="Title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String noteName) {
		this.title = noteName;
	}

	@Column(name="Content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="CreatedTime")
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name="UpdatedTime")
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name="Trash")
	public boolean isTrash() {
		return isTrash;
	}
	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}
	@Column(name="Archive")
	public boolean isArchive() {
		return isArchive;
	}
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}
	@Column(name="Pinned")
	public boolean isPinned() {
		return isPinned;
	}
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}
	
	@Column(name="Color")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Column(name="Reminder")
	public Date getReminder() {
		return reminder;
	}
	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Column(name="sharedNoteId")
	public List<User> getSharedUser() {
		return sharedUser;
	}

	public void setSharedUser(List<User> sharedUser) {
		this.sharedUser = sharedUser;
	}

	@Column(name="image",columnDefinition="mediumblob")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "Notes [noteId=" + noteId + ", title=" + title + ", content=" + content + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", user=" + user + "]";
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "note_label", joinColumns = { @JoinColumn(name = "noteId") })
	public Set<Label> getLabels() {
		return labels;
	}

	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}
	
}
