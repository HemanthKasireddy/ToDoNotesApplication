package com.bridgeit.toDoNotes.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.toDoNotes.DAO.IUserDAO;
import com.bridgeit.toDoNotes.model.User;
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO iUserDAO;

	public long insertUser(User user) {

		long id=iUserDAO.createUser(user);

		if (id > 0) {

			return id;	
		} 
		return -1;
	}

	@Override
	public boolean updateUser(User user) {

		if (iUserDAO.updateUser(user) > 0) {

			return true;

		} 
		return false;

	}

	@Override
	public User getUser(String email, String password) {

		User user=iUserDAO.getUser(email,password);

		if (user == null) {

			return null;
		}

		return user;
	}

	@Override
	public void deleteUser() {


	}

	@Override
	public User getUserById(long id) {

		return iUserDAO.getUserById(id);
	}
	@Override
	public boolean updateUserPassword(String password, String email) {

		if(iUserDAO.updateUserPassword(password,email) > 0) {

			return true;

		} 

		return false;
	}



	public User getUserByEmailId(String email) {

		return iUserDAO.getUserByEmail(email);	
	}

}
