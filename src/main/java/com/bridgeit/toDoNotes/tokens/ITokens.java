package com.bridgeit.toDoNotes.tokens;

public interface ITokens {

	public String generateToken(String subject, String id);
	public String verifyToken(String token);
}
