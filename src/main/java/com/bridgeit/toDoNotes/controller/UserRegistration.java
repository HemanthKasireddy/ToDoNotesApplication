package com.bridgeit.toDoNotes.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.Response;
import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.IMailerService;
import com.bridgeit.toDoNotes.services.UserServiceImpl;
import com.bridgeit.toDoNotes.tokens.ITokens;
import com.bridgeit.toDoNotes.validations.UserRegistrationValidations;

@RestController
public class UserRegistration {
	private  static Logger logger = Logger.getLogger(UserRegistration.class);


	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private IMailerService iMailerService;

	@Autowired
	private UserRegistrationValidations userRegistrationValidations;

	@Autowired
	private ITokens iTokens;

	@RequestMapping(value = "/register", method = RequestMethod.POST)

	public ResponseEntity<Response> registeringUser(@RequestBody User user,HttpServletRequest request ,HttpServletResponse response) {
		Response myResponse=new Response();
		if(userRegistrationValidations.validDetails(user)) {

			long id=userServiceImpl.insertUser(user);

			if(id>0) {

				String token=iTokens.generateToken("Registration",String.valueOf(id));
				String url=String.valueOf(request.getRequestURL());

				url=url.substring(0, url.lastIndexOf("/"))+"/activate/"+token;

				logger.debug(url);
				user.setActivated(false);

				iMailerService.sendMail(user.getEmail(),url);
				myResponse.setResponseMessage(" registered successfully ");
				
			
				return  ResponseEntity.status(HttpStatus.ACCEPTED).body( myResponse) ;

			} else {
				myResponse.setResponseMessage(" bad request");

				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body( myResponse);

			}

		} else {
			myResponse.setResponseMessage("validations fails");
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body( myResponse);

		}
	}

	@RequestMapping(value = "/activate/{token:.+}", method = RequestMethod.GET)

	public ResponseEntity<String> activateUser(@PathVariable("token") String token,HttpServletResponse response) throws IOException{		

		long id=Long.valueOf(iTokens.verifyToken(token));

		if(id>0 ) {

			User user=userServiceImpl.getUserById(id);

			if(user!=null){

				if(userServiceImpl.updateUser(user)){
					response.sendRedirect("http://localhost:8080/ToDoNotesApp/#!/login");
					return ResponseEntity.ok("User Activated");

				} else {

					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in activation");
				}

			} else {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Does not Exist");
			}

		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token is invalid");

		}
	}

}
