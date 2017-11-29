package com.bridgeit.toDoNotes.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	

	@Override
	public String toString() {
		return "Notes [noteId=" + noteId + ", title=" + title + ", content=" + content + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", user=" + user + "]";
	}
	@JsonIgnore
	@ManyToMany
	@Column(name="sharedNoteId")
	public List<User> getSharedUser() {
		return sharedUser;
	}

	public void setSharedUser(List<User> sharedUser) {
		this.sharedUser = sharedUser;
	}
	
}
