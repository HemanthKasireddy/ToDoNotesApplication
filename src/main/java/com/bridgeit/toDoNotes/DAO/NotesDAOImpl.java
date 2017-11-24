package com.bridgeit.toDoNotes.DAO;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.toDoNotes.model.Notes;


public class NotesDAOImpl implements INotesDAO {
	 private  static Logger logger = Logger.getLogger(NotesDAOImpl.class);

	@Autowired
	private   SessionFactory sessionFactory;


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Notes> getAllNotes(long userId) {
		
		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return null;
		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");
		
		session.beginTransaction();
		logger.debug("transaction is opened ");
		logger.debug(userId);
		
		Query query=session.createQuery("from Notes where userId=:userId");
		query.setParameter("userId", userId);
		
		List<Notes> notes=query.list();
		
		session.getTransaction().commit();
		session.close();
		
		Iterator itr=notes.iterator();
		
		while(itr.hasNext()) {
		
			logger.debug("Notes are "+itr.next().toString());
		}
		
		return notes;
	}

	@Override
	public long createNote(Notes notes) {
		
		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return -1;
		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");
		
		session.beginTransaction();
		logger.debug("transaction is opened ");

		long id=(Long) session.save(notes);
		
		session.getTransaction().commit();
		logger.debug("inserting to database ");

		session.close();
		
		return id;
	}

	@Override
	public boolean deleteNote(Notes notes) {
		
		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return false;
		} 
		logger.debug("connection correct");
		try {
			Session session=sessionFactory.openSession();
			logger.debug("session is opened ");
			
			session.beginTransaction();
			logger.debug("transaction is opened ");
			
			
			session.delete(notes);
			
			session.getTransaction().commit();
			session.close();
			
			return true;
			
		} catch(Exception ex) {
			
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Notes getNoteById( long noteId,long userId) {
		
		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return null;
		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");
		
		session.beginTransaction();
		logger.debug("transaction is opened ");
		
		logger.debug(noteId);
		Query query=session.createQuery("from Notes where noteId=:noteId and userId=:userId");
		query.setParameter("noteId",noteId);
		query.setParameter("userId",userId);

		Notes note=(Notes) query.uniqueResult();
		session.getTransaction().commit();
		session.close();
		
		return  note;
	}


	@Override
	public boolean updateNote(Notes notes) {

		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return false;
		} 
		logger.debug("connection correct");
		try {
		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");
		
		session.beginTransaction();
		logger.debug("transaction is opened ");
		session.saveOrUpdate(notes);
		
		session.getTransaction().commit();
		logger.debug("inserting to database ");

		session.close();
		return true;
		}catch(Exception ex)  {
				ex.printStackTrace();
				logger.error(ex);
				return false;
		}

	}
}
