package com.bridgeit.toDoNotes.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="NotesLabel")
public class Label {
	private long id;
	private String labelName;
	private User user;
	private Set<Notes> notes;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LabelId")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="LabelName")
	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@ManyToMany(mappedBy="labels")
	@JsonIgnore
	public Set<Notes> getNotes() {
		
		return notes;
	
	}

	public void setNotes(Set<Notes> notes) {
		this.notes = notes;
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

	@Override
	public String toString() {
		return "Label [id=" + id + ", labelName=" + labelName + ", user=" + user + ", notes=" + notes + "]";
	}

	
}
