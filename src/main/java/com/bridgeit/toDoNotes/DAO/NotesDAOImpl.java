package com.bridgeit.toDoNotes.DAO;

import java.util.Date;
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

	@SuppressWarnings("rawtypes")
	@Override
	public long deleteNote(long userId, long id) {
		
		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return -1;
		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");
		
		session.beginTransaction();
		logger.debug("transaction is opened ");
		
		logger.debug(userId);
		
		Query query=session.createQuery(" delete from Notes where userId=:userId and noteId=:noteId");
		
		query.setParameter("userId", userId);
		query.setParameter("noteId", id);
		
		long responseCount=query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
		
		return responseCount;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Notes> getNoteById(long userId, long noteId) {
		
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
		
		Query query=session.createQuery("from Notes where userId=:userId and noteId=:noteId");
		
		query.setParameter("userId", userId);
		query.setParameter("noteId", noteId);

		List<Notes> notes=query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return  notes;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public int updateNote(Notes notes, long userId, long noteId) {

		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return -1;
		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");
		
		session.beginTransaction();
		logger.debug("transaction is opened ");
		
		Query query=session.createQuery("update Notes set title=:title ,content=:content,updatedTime=:updatedTime  where userId=:userId and noteId=:noteId");
		
		query.setParameter("userId", userId);
		query.setParameter("noteId", noteId);
		query.setParameter("title", notes.getTitle());
		query.setParameter("content", notes.getContent());
		query.setParameter("updatedTime", new Date());
		
		int responseCount=query.executeUpdate();
		
		session.getTransaction().commit();
		logger.debug("inserting to database ");

		session.close();
		return responseCount;
		}

}
