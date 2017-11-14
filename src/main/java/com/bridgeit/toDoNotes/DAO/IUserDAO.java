package com.bridgeit.toDoNotes.DAO;

import com.bridgeit.toDoNotes.model.User;

public interface IUserDAO {

	public long createUser(User user);
	public User getUserById(long id);
	public int updateUser(User user);
	public User getUser(String email, String password);
	public User getUserByEmail(String email);
	public int updateUserPassword(String password, String email);
}
