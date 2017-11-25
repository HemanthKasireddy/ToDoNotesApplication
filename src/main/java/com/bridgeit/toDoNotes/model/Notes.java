package com.bridgeit.toDoNotes.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
//	private boolean isReminder;
	
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
	/*@Column(name="Reminder")
	public boolean isReminder() {
		return isReminder;
	}
	public void setReminder(boolean isReminder) {
		this.isReminder = isReminder;
	}*/

	@Override
	public String toString() {
		return "Notes [noteId=" + noteId + ", title=" + title + ", content=" + content + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", user=" + user + "]";
	}
	
		
	
}
