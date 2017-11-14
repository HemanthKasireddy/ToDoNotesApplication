package com.bridgeit.toDoNotes.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.Notes;
import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.INotesService;
import com.bridgeit.toDoNotes.services.UserServiceImpl;
import com.bridgeit.toDoNotes.tokens.ITokens;

@RestController
/*@RequestMapping(value = "/notes")*/
public class UserNotes {

	private  static Logger logger = Logger.getLogger(UserNotes.class);

	@Autowired
	private INotesService iNotesService;
	@Autowired 
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ITokens iTokens;

	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ResponseEntity<String> createNote(@RequestBody Notes notes, HttpServletRequest request) {

		notes.setCreatedTime(new Date());
		notes.setUpdatedTime(notes.getCreatedTime());

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

		User user=userServiceImpl.getUserById(id);

		notes.setUser(user);

		long responseCount=iNotesService.createNote(notes);

		if(responseCount>0) {

			logger.error("Note is  created ");

			return  ResponseEntity.status(HttpStatus.CREATED).body("Note created succesfully");
		} 
		logger.error("Note is not created ");
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");

	}


	@RequestMapping(value="/getAllNotes",method=RequestMethod.GET)
	public ResponseEntity<List<Notes>> getAllNotes(HttpServletRequest request) {

		Enumeration<String> headerNames = request.getHeaderNames();
		String token=null; ;

		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();
			logger.debug("headers are : "+request.getHeader(key));

			if(key.equals("token")) {

				token = request.getHeader(key);
				break;
			}
		}

		long id=Long.valueOf(iTokens.verifyToken(token));

		List<Notes> notes =iNotesService.getAllNotes(id);

		if(notes==null) {

			//return  ResponseEntity.(HttpStatus.BAD_GATEWAY);

		} 

		return ResponseEntity.ok(notes);

	}


	@RequestMapping(value="/getNodeById/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<Notes>> getNoteById(@PathVariable("id") long noteId,HttpServletRequest request) {

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
		List<Notes> notes =iNotesService.getNoteById(id,noteId);

		if(notes!=null) {


			logger.debug("Notes are "+notes);

			return  ResponseEntity.ok(notes);

		} else {

			return  null;

		}
	}

	@RequestMapping(value="/deleteNote/{id}",method = RequestMethod.POST)
	public ResponseEntity<String> deleteNote(@PathVariable("id") long id,HttpServletRequest request) {

		Enumeration<String> headerNames = request.getHeaderNames();
		String token=null; ;

		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();

			if(key.equals("token")) {

				token = request.getHeader(key);
				break;

			}
		}

		long userId=Long.valueOf(iTokens.verifyToken(token));
		long responseCount =iNotesService.deleteNote(userId,id);

		if(responseCount>0) {

			return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Note deleted succesfully");

		} else {

			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Note deleted unsuccesfully");

		}
	}

	@RequestMapping(value="/updateNote/{id}", method= RequestMethod.POST)
	public ResponseEntity<String> updateNote(@RequestBody Notes notes,@PathVariable("id") long id,HttpServletRequest request) {

		Enumeration<String> headerNames = request.getHeaderNames();
		String token=null; ;

		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();
			if(key.equals("token")) {

				token = request.getHeader(key);
				break;
			}
		}

		long userId=Long.valueOf(iTokens.verifyToken(token));
		long responseCount =iNotesService.updateNote(notes,userId,id);

		if(responseCount>0) {

			return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Note updated succesfully");

		} else {

			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Note updated unsuccesfully");

		}
	}
}
