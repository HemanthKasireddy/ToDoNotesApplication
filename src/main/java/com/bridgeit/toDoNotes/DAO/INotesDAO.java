package com.bridgeit.toDoNotes.DAO;

import java.util.List;

import com.bridgeit.toDoNotes.model.Notes;

public interface INotesDAO {
	public long createNote(Notes notes);
	public List<Notes> getNoteById(long userId, long noteId);
	public List<Notes> getAllNotes(long userId);
	public int updateNote(Notes notes, long userId, long noteId);
	public long deleteNote(long userId, long id);
}
