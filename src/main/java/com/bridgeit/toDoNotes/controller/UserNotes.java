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


	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public ResponseEntity<User> getuser( HttpServletRequest request) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@inside get user");
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

		User user1=userServiceImpl.getUserById(Long.valueOf(iTokens.verifyToken(token)));
		
System.out.println("@@@@@@##############@@@@@@@@@@@@####################"+user1);
		return ResponseEntity.ok(user1);

	}

	@RequestMapping(value = "/getOwner", method = RequestMethod.POST)
	public ResponseEntity<User> getOwner(@RequestBody Notes note, HttpServletRequest request){
		String token=request.getHeader("token");
		long id=Long.valueOf(iTokens.verifyToken(token));

		User user1=userServiceImpl.getUserById(id);
		if(user1!=null) {
		Notes userNote=iNotesService.getNoteById(id, note.getNoteId());
		User owner=userNote.getUser();
		return ResponseEntity.ok(owner);
		}
		else{
			return ResponseEntity.ok(null);
		}
	}
	
	@RequestMapping(value = "/sharedNotesUser", method = RequestMethod.POST)
	public ResponseEntity<List<User>> sharedNotesUser(@RequestBody Notes note, HttpServletRequest request){
		
		logger.debug("********************************* inside ge tshared note user");
		String token=request.getHeader("token");
		long id=Long.valueOf(iTokens.verifyToken(token));

		User user1=userServiceImpl.getUserById(id);
		if(user1!=null) {
			
			Notes userNote=iNotesService.getNoteById(id, note.getNoteId());
			if(userNote==null) {
				return null;
			}
		
			List<User> owner=userNote.getSharedUser();
		return ResponseEntity.ok(owner);
		}
		else{
			return ResponseEntity.ok(null);
		}
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
	

	@RequestMapping(value="/collaborator", method= RequestMethod.POST)
	public ResponseEntity<Response> collaborator(@RequestBody Notes notes,HttpServletRequest request) {
	logger.debug("@@@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!##############!!!!!!!!!!!# inside colleborator api");
		Response myResponse=new Response();
		String token=request.getHeader("token"); 
		long userId=Long.valueOf(iTokens.verifyToken(token));

		if(userId<0) {
			myResponse.setResponseMessage("Token is expired");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(myResponse);
		}
		Notes oldNote=iNotesService.getNote(notes.getNoteId());
		User sharedUser=userServiceImpl.getUserByEmailId(request.getHeader("emailId"));
		oldNote.getSharedUser().add(sharedUser);
		if(iNotesService.updateNote(oldNote, oldNote.getUser().getUserId())) {
			return  ResponseEntity.status(HttpStatus.ACCEPTED).body(myResponse);

		}
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);

/*
		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();
			if(key.equals("token")) {

				token = request.getHeader(key);
				break;
			}
		}
		long userId=Long.valueOf(iTokens.verifyToken(token));
		SharedNotes sharedNotes=new SharedNotes();
		if(userId<0) {
			myResponse.setResponseMessage("Token is expired");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(myResponse);
		}
		
		
		List<User> sharedUser=notes.getSharedUser();
	
		sharedNotes.setNoteId(notes.getNoteId());
		sharedNotes.setShareuserId(sharedUser.getUserId());
		myResponse.setResponseMessage("Shared success");
		return ResponseEntity.status(HttpStatus.CREATED).body(myResponse);*/
		
	}
}
