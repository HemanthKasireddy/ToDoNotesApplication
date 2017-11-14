package com.bridgeit.toDoNotes.services;
import java.util.List;

import com.bridgeit.toDoNotes.model.Notes;;
public interface INotesService {
	
	public long createNote(Notes notes);
	public List<Notes> getNoteById(long userId, long noteId);
	public List<Notes> getAllNotes(long userId);
	public int updateNote(Notes notes, long userId, long noteId);
	public long deleteNote(long userId, long id);
}
