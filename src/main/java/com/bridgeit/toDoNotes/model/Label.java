package com.bridgeit.toDoNotes.model;

import java.util.Set;

import javax.persistence.Entity;

@Entity(name="NotesLabel")
public class Label {

	private String labelName;
	private Set<User> user;
	private Set<Notes> notes;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Set<Notes> getNotes() {
		
		return notes;
	
	}

	public void setNotes(Set<Notes> notes) {
		this.notes = notes;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}
}
