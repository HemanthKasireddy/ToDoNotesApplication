package com.bridgeit.toDoNotes.services;
import java.util.List;

import com.bridgeit.toDoNotes.model.Label;
import com.bridgeit.toDoNotes.model.Notes;
public interface INotesService {
	
	public long createNote(Notes notes);
	public Notes getNoteById(long userId);
	public List<Notes> getAllNotes(long userId);
	public boolean updateNote(Notes notes);
	public boolean deleteNote(Notes notes );
	public Notes getNote(long noteId);
	public List<Notes> getSharedNotes(long id);
	public long createLabel(Label label);
}
