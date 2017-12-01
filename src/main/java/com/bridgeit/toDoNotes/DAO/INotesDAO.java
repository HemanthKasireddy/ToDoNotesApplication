package com.bridgeit.toDoNotes.DAO;

import java.util.List;

import com.bridgeit.toDoNotes.model.Notes;

public interface INotesDAO {
	public long createNote(Notes notes);
	public Notes getNoteById(long userId, long noteId);
	public List<Notes> getAllNotes(long userId);
	public boolean updateNote(Notes notes);
	public boolean deleteNote(Notes notes);
	public Notes getNote(long noteId);
	public List<Notes> getSharedNotes(long id);
}
