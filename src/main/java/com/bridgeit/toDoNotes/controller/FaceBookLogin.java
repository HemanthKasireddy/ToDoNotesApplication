package com.bridgeit.toDoNotes.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.Response;
import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.UserServiceImpl;
import com.bridgeit.toDoNotes.socialLogging.FaceBookConnection;
import com.bridgeit.toDoNotes.tokens.ITokens;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class FaceBookLogin {

	private  static Logger logger = Logger.getLogger(FaceBookLogin.class);

	@Autowired
	private FaceBookConnection faceBookConnection;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ITokens iTokens;
	@RequestMapping(value="/logInWithFaceBook")
	public void facebookConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String unid=UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String faceBookLogInURL=faceBookConnection.getFaceBookURL(unid);

		logger.debug("googleLoginURL  " + faceBookLogInURL);

		response.sendRedirect(faceBookLogInURL);
	}
	@RequestMapping(value="/connectFaceBook")
	public void redirectFromfacebook(HttpServletRequest request, HttpServletResponse response ,HttpSession session) throws IOException {
		
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

			logger.debug(" user is new to our db");

			user = new User();

			user.setName(profile.get("name").asText());
			user.setEmail(profile.get("email").asText());
			user.setActivated(true);
			user.setPicUrl(profile.get("picture").get("data").get("url").asText());
			userServiceImpl.insertUser(user);
			User user1 = userServiceImpl.getUserByEmailId(profile.get("email").asText());
			String token=iTokens.generateToken("login",String.valueOf(user1.getUserId()));
			session.setAttribute("token", token);

			//myResponse.setResponseMessage(token);
			response.sendRedirect("http://localhost:8080/ToDoNotesApp/#!/dummy");
		} else {
			String token=iTokens.generateToken("login",String.valueOf(user.getUserId()));
			logger.debug(" user is already existin our db");
		//	myResponse.setResponseMessage(token);
			logger.debug("token generated for facebook log in"+token);
			session.setAttribute("token", token);
			response.sendRedirect("http://localhost:8080/ToDoNotesApp/#!/dummy");

		}

	}
	@RequestMapping(value="/getToken")
	public ResponseEntity<Response> getToken(HttpSession session,HttpServletResponse response){
		logger.debug("in side social login tken genrator");
		System.out.println("@#@@@@@@@@@@@@@@@@@@@@@@@@###############@@@@@@@@@@@########@########################@@@@@@@@@@@@############@@@@@@@@@@@@########");
		Response myResponse = new Response() ;
		response.setHeader("Authorazation",(String) session.getAttribute("token"));

		 myResponse.setResponseMessage((String) session.getAttribute("token"));
		 System.out.println( session.getAttribute("token").toString());
		session.removeAttribute("token");
		return  ResponseEntity.ok(myResponse);
	}
}
