package com.bridgeit.toDoNotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.bridgeit.toDoNotes.DAO.INotesDAO;
import com.bridgeit.toDoNotes.DAO.IUserDAO;
import com.bridgeit.toDoNotes.DAO.NotesDAOImpl;
import com.bridgeit.toDoNotes.DAO.UserDAO;
import com.bridgeit.toDoNotes.controller.UserRegistration;
import com.bridgeit.toDoNotes.model.Notes;
import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.IMailerService;
import com.bridgeit.toDoNotes.services.INotesService;
import com.bridgeit.toDoNotes.services.MailerServiceImpl;
import com.bridgeit.toDoNotes.services.NotesServiceImpl;
import com.bridgeit.toDoNotes.services.UserServiceImpl;
import com.bridgeit.toDoNotes.socialLogging.FaceBookConnection;
import com.bridgeit.toDoNotes.socialLogging.GoogleConnection;
import com.bridgeit.toDoNotes.tokens.ITokens;
import com.bridgeit.toDoNotes.tokens.TokenImpl;
import com.bridgeit.toDoNotes.validations.UserRegistrationValidations;

@Configuration
@ComponentScan("com.bridgeit.toDoNotes")
public class ApplicationConfiguration {

	@Bean
	public User user() {

		return new User();
	}
	@Bean
	public Notes notes() {
		return new Notes();
	}
	@Bean
	public UserRegistration userRegistration() {
		return new UserRegistration();
	}
	@Bean
	public UserServiceImpl userServiceImpl() {
		return new UserServiceImpl();
	}
	@Bean
	public IUserDAO iUserDAO() {
		return new UserDAO();
	}
	@Bean
	public IMailerService iMailerService() {
		return new MailerServiceImpl();
	}
	@Bean
	public UserRegistrationValidations userRegistrationValidations() {
		return new UserRegistrationValidations();
	}
	@Bean
	public INotesService iNotesService() {
		return new NotesServiceImpl ();
	}
	@Bean
	public INotesDAO iNotesDAO() {
		return new NotesDAOImpl();

	}
	@Bean
	public ITokens iTokens() {
		return new TokenImpl();
	}
	@Bean
	public GoogleConnection googleConnection() {
		return new GoogleConnection();
	}
	@Bean
	public FaceBookConnection faceBookConnection() {
		return new FaceBookConnection();
	}
}
