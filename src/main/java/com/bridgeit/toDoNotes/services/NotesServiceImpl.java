package com.bridgeit.toDoNotes.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgeit.toDoNotes.DAO.INotesDAO;
import com.bridgeit.toDoNotes.model.Notes;

public class NotesServiceImpl implements INotesService {

	private  static Logger logger = Logger.getLogger(NotesServiceImpl.class);

	@Autowired
	private INotesDAO iNotesDAO;

	@Override
	public long createNote(Notes notes) {

		return iNotesDAO.createNote(notes);
	}
	@Override
	public List<Notes> getAllNotes(long userId) {

		List<Notes> notes= iNotesDAO.getAllNotes(userId);

		logger.debug("Notes are "+notes);

		return notes;

	}


	@Override
	public boolean deleteNote(Notes notes, long userId) {
		if(iNotesDAO.getNoteById(notes.getNoteId(), userId)==null) {
			return false;
		}
		notes.setTrash(true);
		return iNotesDAO.deleteNote(notes);
	}


	@Override
	public List<Notes> getNoteById(long userId, long noteId) {

		return iNotesDAO.getNoteById(userId,noteId);

	}

	@Override
	public boolean updateNote(Notes notes) {
		return iNotesDAO.updateNote(notes);

	}
	

}
