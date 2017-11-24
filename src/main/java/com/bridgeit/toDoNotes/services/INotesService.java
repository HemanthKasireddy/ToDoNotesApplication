package com.bridgeit.toDoNotes.services;
import java.util.List;

import com.bridgeit.toDoNotes.model.Notes;;
public interface INotesService {
	
	public long createNote(Notes notes);
	public Notes getNoteById(long userId, long noteId);
	public List<Notes> getAllNotes(long userId);
	public boolean updateNote(Notes notes,long userId);
	public boolean deleteNote(Notes notes ,long userId);
}
