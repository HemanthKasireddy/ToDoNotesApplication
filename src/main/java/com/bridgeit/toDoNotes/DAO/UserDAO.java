package com.bridgeit.toDoNotes.DAO;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bridgeit.toDoNotes.model.User;

public class UserDAO implements IUserDAO {

	private  static Logger logger = Logger.getLogger(UserDAO.class);

	@Autowired
	private   SessionFactory sessionFactory;

	@Override
	public long createUser(User user) {

		if(sessionFactory==null) {

			logger.fatal("connection incorrect");
			
			return -1;

		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");

		session.beginTransaction();
		logger.debug("transaction is opened ");

		if(user.getPassword()!=null) {

			String encryptPassword=BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			user.setPassword(encryptPassword);

		}

		long id=(Long) session.save(user);

		session.getTransaction().commit();
		logger.debug("inserting to database ");

		session.close();

		return id;
	}


	@Override
	public User getUserById(long id) {

		if(sessionFactory==null) {

			logger.fatal("connection incorrect");
			
			return null;

		} 
		logger.debug("connection correct");


		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");

		session.beginTransaction();
		logger.debug("transaction is opened ");

		User user1=session.get(User.class, id);

		session.getTransaction().commit();
		logger.debug(" up dated inserting to database ");
		if(user1==null) {
			logger.debug("@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$ in dao get user by id is null");
		}
		
		session.close();

		return user1;

	}


	@Override
	@SuppressWarnings("rawtypes")
	public int updateUser(User user) {

		if(sessionFactory == null) {
			
			logger.fatal("connection incorrect");
			
			return -1;
		} 

		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");

		session.beginTransaction();
		logger.debug("transaction is opened ");

		Query query = session.createQuery("update User set activated=:activated where id=:id");
		//User user=session.load(User.class, new Long(id));
		user.setActivated(true);

		query.setParameter("activated", user.isActivated());
		query.setParameter("id",user.getUserId());

		int numberOfRows=query.executeUpdate();

		session.getTransaction().commit();
		logger.debug("inserting to database ");

		session.close();

		logger.debug("numberOfRows"+numberOfRows);

		return numberOfRows;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public User getUser(String email, String password) {

		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return null;
		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");

		session.beginTransaction();
		logger.debug("transaction is opened ");

		Query query=session.createQuery("from User where UserEmail=:UserEmail");
		query.setParameter("UserEmail",email);
		//logger.debug(password);
		//query.setParameter("password",BCrypt.password);

		User user1=(User) query.uniqueResult();

		session.getTransaction().commit();
		session.close();

		if(BCrypt.checkpw(password,user1.getPassword())) {

			return user1;

		} 
		return null;

	}

	@Override
	@SuppressWarnings("rawtypes")
	public int updateUserPassword(String password, String email) {

		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return -1;
		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");

		session.beginTransaction();
		logger.debug("transaction is opened ");

		logger.debug("user new password is "+password);

		String encryptPassword=BCrypt.hashpw(password, BCrypt.gensalt());
		//user.setPassword(encryptPassword);

		Query query = session.createQuery("update User set UserPassword=:UserPassword where UserEmail=:UserEmail");

		query.setParameter("UserPassword", encryptPassword);
		query.setParameter("UserEmail", email);

		int numberOfRows=query.executeUpdate();

		session.getTransaction().commit();
		logger.debug("inserting to database ");

		session.close();

		return numberOfRows;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public User getUserByEmail(String email) {

		if(sessionFactory == null) {

			logger.fatal("connection incorrect");

			return null;
		} 
		logger.debug("connection correct");

		Session session=sessionFactory.openSession();
		logger.debug("session is opened ");

		session.beginTransaction();
		logger.debug("transaction is opened ");

		/*Query query=session.createQuery("from User where UserEmail=:UserEmail");
		query.setParameter("UserEmail",email);*/
		//logger.debug(password);
		//query.setParameter("password",BCrypt.password);
		/*User user1=session.get(User.class, email);*/
		System.out.println("#$$$$$$$$$$$$$$##############$$$$$$$$$$######$$$$$# email is"+email);
		User user1=(User) session.createQuery("from User where email = :email")
		           .setParameter("email", email)
		           .uniqueResult();


		session.getTransaction().commit();
		logger.debug(" up dated inserting to database ");

		session.close();
		
		if(user1==null) {
			System.out.println("~!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~~~~~ user in get email by id is null");
		}
		return user1;
	}

}
