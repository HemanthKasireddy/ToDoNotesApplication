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
import com.bridgeit.toDoNotes.model.Response;
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
	public ResponseEntity<Response> createNote(@RequestBody Notes notes, HttpServletRequest request) {
		Response myResponse=new Response();
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
			myResponse.setResponseMessage("Note created succesfully");
			return ResponseEntity.status(HttpStatus.CREATED).body(myResponse);
		} 
		logger.error("Note is not created ");
		myResponse.setResponseMessage("Note is not created");

		   return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);

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
	public ResponseEntity<Notes> getNoteById(@PathVariable("id") long noteId,HttpServletRequest request) {

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
		Notes notes =iNotesService.getNoteById(id,noteId);

		if(notes!=null) {


			logger.debug("Notes are "+notes);

			return  ResponseEntity.ok(notes);

		} else {

			return  null;

		}
	}

	@RequestMapping(value="/deleteNote",method = RequestMethod.POST)
	public ResponseEntity<Response> deleteNote(@RequestBody Notes notes ,HttpServletRequest request) {
		System.out.println("delete Api");
		Enumeration<String> headerNames = request.getHeaderNames();
		String token=null; ;
		Response myResponse=new Response();

		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();

			if(key.equals("token")) {

				token = request.getHeader(key);
				break;

			}
		}

		long userId=Long.valueOf(iTokens.verifyToken(token));

		if( iNotesService.deleteNote(notes ,userId) ) {

			myResponse.setResponseMessage("Note deleted succesfully");
			
			return ResponseEntity.ok(myResponse);

		} else {

			myResponse.setResponseMessage("Note is not deletd");

			   return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
		}
	}

	@RequestMapping(value="/updateNote", method= RequestMethod.POST)
	public ResponseEntity<Response> updateNote(@RequestBody Notes notes,HttpServletRequest request) {
		Response myResponse=new Response();
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
		
		boolean isUpdated=iNotesService.updateNote(notes ,userId);

		if(isUpdated) {

			return  ResponseEntity.status(HttpStatus.ACCEPTED).body(myResponse);

		} else {

			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);

		}
	}
}
