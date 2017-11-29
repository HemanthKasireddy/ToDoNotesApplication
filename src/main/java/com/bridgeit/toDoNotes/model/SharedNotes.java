package com.bridgeit.toDoNotes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="SharedNotes")
public class SharedNotes {
private long  noteId;
private long shareuserId;

@ManyToMany
@JoinColumn(name="sharedNoteId")
public long getShareuserId() {
	return shareuserId;
}
public void setShareuserId(long shareuserId) {
	this.shareuserId = shareuserId;
}
@Column(name="noteId")
public long getNoteId() {
	return noteId;
}
public void setNoteId(long noteId) {
	this.noteId = noteId;
}
}
