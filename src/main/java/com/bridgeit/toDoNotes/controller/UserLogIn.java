package com.bridgeit.toDoNotes.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.Response;
import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.IMailerService;
import com.bridgeit.toDoNotes.services.UserServiceImpl;
import com.bridgeit.toDoNotes.tokens.ITokens;

@RestController
public class UserLogIn {

	private static Logger logger = Logger.getLogger(UserLogIn.class);

	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ITokens iTokens;
	@Autowired
	private IMailerService iMailerService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> logInUser(@RequestBody User userDetails, HttpSession session, HttpServletResponse response) {

		Response myResponse = new Response();
		logger.debug("inside Login");
		User user = userServiceImpl.getUser(userDetails.getEmail(), userDetails.getPassword());

		if (user != null) {

			if (user.isActivated()) {

				String token = iTokens.generateToken("LogIn", String.valueOf(user.getUserId()));

				response.setHeader("Authorazation",token);
				myResponse.setResponseMessage("log in sucess");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body( myResponse);

			} else {
				myResponse.setResponseMessage("user is not activated");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
			}

		} else {

			myResponse.setResponseMessage("pasword is in correct");
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
		}

	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<String> forgotPassword(@RequestBody User userDetails, HttpServletRequest request) {

		User user = userServiceImpl.getUserByEmailId(userDetails.getEmail());

		String token = iTokens.generateToken("ForgotPassword", String.valueOf(user.getUserId()));

		String url = String.valueOf(request.getRequestURL());

		url = url.substring(0, url.lastIndexOf("/")) + "/resetPassword/" + token;

		logger.debug(url);

		iMailerService.sendMail(userDetails.getEmail(), url);

		return ResponseEntity.status(HttpStatus.CREATED).body("link is sent your mail ");

	}
	
	@RequestMapping(value = "/getuserByEmail", method = RequestMethod.GET)
	public ResponseEntity<User> getuserByEmail(@RequestHeader("emailId") String emailId, HttpServletRequest request) {

		Enumeration<String> headerNames = request.getHeaderNames();
		String token = null;
		
System.out.println("!@!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@!!!!!!!!@@@@@@@@@ inside of get user token is: "+request.getHeader("token"));
		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();

			if (key.equals("token")) {

				token = request.getHeader(key);
				break;

			}
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@!@@!!!!!!!!!!!!!!!!!!!!!!!!"+emailId);
		long id = Long.valueOf(iTokens.verifyToken(token));

		logger.debug("@@@@@@@@@@@@@@@@@@@@@token id is " + id);

		if (id > 0) {
			logger.debug("id is  valid ");
			System.out.println("valid0000000000000000000000000000000000000000");

			User user = userServiceImpl.getUserById(id);
			logger.debug("@@@@@@@@@@@@@@@@@@@@@user is " +user);
			
			if (user != null) {
				User user1=userServiceImpl.getUserByEmailId(emailId);
				if (user1==null) {
					logger.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~^^^^^^^^^^^^^^^^^^^^^^^6in side null user");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

				} 
				System.out.println("#########@@@@@@@@@@###############@@@@@@@@@@@@@@@@@@@@@@success"+user1);
				return ResponseEntity.ok(user1);

			} else {
				System.out.println("user is not there in data base+++++++++++++++++++++++++++++++++");
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

		}
	}
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<String> resetPassword(@RequestBody User userDetails, String password, HttpServletRequest request) {

		Enumeration<String> headerNames = request.getHeaderNames();
		String token = null;
		

		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();

			if (key.equals("token")) {

				token = request.getHeader(key);
				break;

			}
		}

		long id = Long.valueOf(iTokens.verifyToken(token));

		logger.debug("token id is " + id);

		if (id > 0) {

			User user = userServiceImpl.getUserById(id);

			if (user != null) {

				if (userServiceImpl.updateUserPassword(userDetails.getPassword(), user.getEmail())) {

					return ResponseEntity.ok("password changed");

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
