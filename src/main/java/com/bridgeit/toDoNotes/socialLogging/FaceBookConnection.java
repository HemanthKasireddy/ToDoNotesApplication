package com.bridgeit.toDoNotes.socialLogging;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FaceBookConnection {
	
	private  static Logger logger = Logger.getLogger(FaceBookConnection.class);
	
	private static final String faceBookClientId="360771361000827";
	private static final String faceBookSecretId="4b2b1f5d288e6bf1cd070b36e38c3280";
	public static final String Redirect_URI = "http://localhost:8080/ToDoNotesApp/connectFaceBook";
	
	public String getFaceBookURL(String unid) {
		String facebookLoginURL = "";

		try {
			
			facebookLoginURL = "http://www.facebook.com/dialog/oauth?" + "client_id=" + faceBookClientId + "&redirect_uri="
					+ URLEncoder.encode(Redirect_URI, "UTF-8") + "&state=" + unid + "&response_type=code"
					+ "&scope=public_profile,email";
		
		} catch (UnsupportedEncodingException e) {
			
			logger.error("Errr is : ",e);
			e.printStackTrace();
		}
		logger.debug("inside facebook authentication" + facebookLoginURL);
		
		return facebookLoginURL;
		
	}

	public String getAccessToken(String authCode) throws UnsupportedEncodingException {
		
		String fbaccessTokenURL = "https://graph.facebook.com/v2.9/oauth/access_token?" + "client_id=" + faceBookClientId
				+ "&redirect_uri=" + URLEncoder.encode(Redirect_URI, "UTF-8") + "&client_secret=" + faceBookSecretId + "&code="
				+ authCode;

		ResteasyClient restCall = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = restCall.target(fbaccessTokenURL);
		
		Form form = new Form();
		form.param("client_id", faceBookClientId);
		form.param("client_secret", faceBookSecretId);
		form.param("redirect_uri", Redirect_URI);
		form.param("code", authCode);
		form.param("grant_type", "authorization_code");
		
		// using post request we are sending an data to web service and getting
		// json response back
		
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		logger.debug("resp ::" + response);

		String facebookAccessToken = response.readEntity(String.class);
		
		ObjectMapper mapper=new ObjectMapper();
		
		String acc_token = null;
		
		try {
			
			acc_token = mapper.readTree(facebookAccessToken).get("access_token").asText();
		
		} catch (IOException e) {
			
			logger.error("Error is :"+e);
			e.printStackTrace();
		
		}
		
		return acc_token;

	}

	public JsonNode getUserProfile(String fbaccessToken) {
		
		String fbgetUserURL = "https://graph.facebook.com/v2.9/me?access_token=" + fbaccessToken
				+ "&fields=id,name,email,picture";
		logger.debug("fb get user details " + fbgetUserURL);
		
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(fbgetUserURL);

		String headerAuth = "Bearer " + fbaccessToken;
		Response response = target.request()
				.header("Authorization", headerAuth)
				.accept(MediaType.APPLICATION_JSON)
				.get();
		
		String profile =  response.readEntity(String.class);
		
		ObjectMapper mapper=new ObjectMapper();
		
		JsonNode FBprofile = null;
		
		try {
			
			FBprofile = mapper.readTree(profile);
		
		} catch (IOException e) {
			
			logger.debug("User profile is not found",e);
			e.printStackTrace();
	
		}
		
		restCall.close();
		
		return FBprofile;

	}

}
