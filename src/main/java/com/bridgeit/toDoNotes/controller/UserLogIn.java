package com.bridgeit.toDoNotes.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.IMailerService;
import com.bridgeit.toDoNotes.services.UserServiceImpl;
import com.bridgeit.toDoNotes.tokens.ITokens;

@RestController
public class UserLogIn {

	private  static Logger logger = Logger.getLogger(UserLogIn.class);

	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ITokens iTokens; 
	@Autowired
	private IMailerService iMailerService;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> logInUser(@RequestBody User userDetails,HttpSession session) {

		logger.debug("inside Login");
		User user=userServiceImpl.getUser(userDetails.getEmail(),userDetails.getPassword());

		if(user!=null) {

			if(user.isActivated()) {

				String token=iTokens.generateToken("LogIn", String.valueOf(user.getUserId()));

				/*HttpSession session=((HttpServletRequest) request).getSession();*/
				/*					System.out.println("####### Usre id is: "+user.getUserId()+" user name is "+user.getEmail());
				 */					
				//session.setAttribute("Name", user);

				return  ResponseEntity.status(HttpStatus.CREATED).body("User log in succesfully with token of  : "+token );

			} else {

				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user is not verified please click on registration link ") ;

			}


		} else {

			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user email or password are wrong");

		}

	}


	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<String> forgotPassword(@RequestBody User userDetails,HttpServletRequest request) {

		User user=userServiceImpl.getUserByEmailId(userDetails.getEmail());

		String token=iTokens.generateToken("ForgotPassword", String.valueOf(user.getUserId()));

		String url=String.valueOf(request.getRequestURL());

		url=url.substring(0, url.lastIndexOf("/"))+"/resetPassword/"+token;

		logger.debug(url);

		iMailerService.sendMail(userDetails.getEmail(),url);

		return  ResponseEntity.status(HttpStatus.CREATED).body("link is sent your mail ");

	}


	@RequestMapping(value = "/resetPassword/{password}", method = RequestMethod.POST)
	public ResponseEntity<String> resetPassword(@PathVariable("password") String password,HttpServletRequest request){		

		Enumeration<String> headerNames = request.getHeaderNames();
		String token=null; ;

		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();

			if(key.equals("token")) {

				token = request.getHeader(key);
				break;

			}
		}

		long id=Long.valueOf(iTokens.verifyToken(token));

		logger.debug("token id is "+ id);

		if(id>0 ) {

			User user=userServiceImpl.getUserById(id);

			if(user!=null){

				if(userServiceImpl.updateUserPassword(password,user.getEmail())){

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

