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
import com.bridgeit.toDoNotes.socialLogging.FaceBookConnection;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class FaceBookLogin {

	private  static Logger logger = Logger.getLogger(FaceBookLogin.class);

	@Autowired
	private FaceBookConnection faceBookConnection;
	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping(value="/logInWithFaceBook")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String unid=UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String faceBookLogInURL=faceBookConnection.getFaceBookURL(unid);

		logger.debug("googleLoginURL  " + faceBookLogInURL);

		response.sendRedirect(faceBookLogInURL);
	}
	@RequestMapping(value="/connectFaceBook")
	public ResponseEntity<User> redirectFromGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");

		logger.debug("in connect facebook");

		if (sessionState == null || !sessionState.equals(googlestate)) {

			response.sendRedirect("loginWithFacebook");

		}

		String error = request.getParameter("error");

		if (error != null && error.trim().isEmpty()) {

			response.sendRedirect("userlogin");

		}

		String authCode = request.getParameter("code");

		String fbaccessToken = faceBookConnection.getAccessToken(authCode);

		logger.debug("fbaccessToken " + fbaccessToken);

		JsonNode profile = faceBookConnection.getUserProfile(fbaccessToken);
		logger.debug("fb profile :" + profile);

		User user = userServiceImpl.getUserByEmailId(profile.get("email").asText());

		logger.debug("fb img "+ profile.get("picture").get("data").get("url").asText());

		// get user profile
		if (user == null) {

			System.out.println(" user is new to our db");

			user = new User();

			user.setName(profile.get("name").asText());
			user.setEmail(profile.get("email").asText());
			user.setActivated(true);
			userServiceImpl.insertUser(user);
			//response.sendRedirect("/home");

			return new ResponseEntity<User>(user,HttpStatus.CREATED);

		} else {
			//response.sendRedirect("/home");
			return new ResponseEntity<User>(user,HttpStatus.CONFLICT);
		}

	}
}
