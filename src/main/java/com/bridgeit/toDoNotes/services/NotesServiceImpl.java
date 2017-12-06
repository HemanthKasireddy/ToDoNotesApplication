package com.bridgeit.toDoNotes.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgeit.toDoNotes.DAO.INotesDAO;
import com.bridgeit.toDoNotes.model.Label;
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
	public boolean deleteNote(Notes notes) {
	
		if(iNotesDAO.getNoteById(notes.getNoteId())==null) {
			return false;
		}
		
		return iNotesDAO.deleteNote(notes);
	}


	@Override
	public Notes getNoteById(long noteId) {

		return iNotesDAO.getNoteById(noteId);

	}

	@Override
	public boolean updateNote(Notes notes) {
		/*Notes notesObject= iNotesDAO.getNoteById(notes.getNoteId(), userId);
		
		if(notesObject==null) {
			Notes sharedUserNote=iNotesDAO.getsharedUserNoteById(notes.getNoteId(), userId);
			if(sharedUserNote==null) {
				return false;
			}
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@00000000shared user "+sharedUserNote.getSharedUser());
			notes.setSharedUser(sharedUserNote.getSharedUser());
			notes.setUser(sharedUserNote.getUser());
			
			return iNotesDAO.updateNote(notes);
		}
		System.out.println("########################################## owner "+notesObject.getSharedUser());
		
		if(notesObject.getSharedUser()==null) {
		
			notes.setUser(notesObject.getUser());
			return iNotesDAO.updateNote(notes);
		
		}
		
		notes.setSharedUser(notesObject.getSharedUser());
		notes.setUser(notesObject.getUser());*/
		
		return iNotesDAO.updateNote(notes);

	}
	@Override
	public Notes getNote(long noteId) {

		return iNotesDAO.getNote(noteId);
	}
	@Override
	public List<Notes> getSharedNotes(long id) {
		List<Notes> sharedNotes=iNotesDAO.getSharedNotes(id);
		return sharedNotes;
	}
	@Override
	public long createLabel(Label label) {

		
		return iNotesDAO.createLabel(label);
	}
	

}
