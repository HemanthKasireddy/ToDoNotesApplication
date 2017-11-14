package com.bridgeit.toDoNotes.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.UserServiceImpl;
import com.bridgeit.toDoNotes.socialLogging.GoogleConnection;
import com.fasterxml.jackson.databind.JsonNode;
@RestController
public class GoogleLogIn {

	private  static Logger logger = Logger.getLogger(GoogleLogIn.class);

	@Autowired
	private GoogleConnection googleConnection;
	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping(value="/logInWithGoogle")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String unid=UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);

		String googleLogInURL=googleConnection.getGoogleURL(unid);

		logger.debug("googleLoginURL  " + googleLogInURL);

		response.sendRedirect(googleLogInURL);
		return;
	}

	@RequestMapping(value="/connectGoogle")
	public ResponseEntity<User> redirectFromGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");

		if (sessionState == null || !sessionState.equals(googlestate)) {

			response.sendRedirect("loginWithGoogle");

		}

		logger.debug("inside connectGoogle");
		String error = request.getParameter("error");

		// change this to the front end homepage address

		if (error != null && error.trim().isEmpty()) {

			response.sendRedirect("userlogin");

		}

		String authCode=request.getParameter("code");
		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2Auth code is "+authCode);

		String googleaccessToken = googleConnection.getAccessToken(authCode);
		logger.debug("accessToken " + googleaccessToken);

		JsonNode profile = googleConnection.getUserProfile(googleaccessToken);

		logger.debug("google profile :"+profile);
		logger.debug("google profile :" + profile.get("displayName"));
		logger.debug("user email in google login :" + profile.get("emails").get(0).get("value").asText()); //asText() is use to remove outer text.
		logger.debug("google profile :" + profile.get("image").get("url"));

		User user = userServiceImpl.getUserByEmailId(profile.get("emails").get(0).get("value").asText());

		// get user profile
		if (user == null) {

			logger.debug(" user is new to our db");

			user = new User();

			user.setName(profile.get("displayName").asText());
			user.setEmail(profile.get("emails").get(0).get("value").asText());

			//user.setPassword("");
			//user.getMobileNumber(profile.get(arg0));

			user.setActivated(true);
			//inserting a user details in to database 
			userServiceImpl.insertUser(user);

			//response.sendRedirect("/home");

			return new ResponseEntity<User>(user,HttpStatus.CREATED);

		} else {

			//response.sendRedirect("/home");

			return new ResponseEntity<User>(user,HttpStatus.CONFLICT);

		}

	}
}
