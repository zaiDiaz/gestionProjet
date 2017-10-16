package com.stock.mvc.dao.impl;

import java.io.InputStream;

import javax.swing.JOptionPane;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.stock.mvc.dao.IFlickrDao;

public class FlickrDaoImpl implements IFlickrDao {

	private Flickr flickr;

	private UploadMetaData uploadMetaData = new UploadMetaData();

	private String apiKey = "f96618942307a4c23e7e88e449c14b5b";

	private String sharedSecret = "d740d92fdba6ccd1";
	
	private void connect() {
		flickr = new Flickr(apiKey, sharedSecret, new REST());
		Auth auth = new Auth();
		auth.setPermission(Permission.READ);
		auth.setToken("72157686236938672-d3dd7951e3a7887d");
		auth.setTokenSecret("a47b75c353c99a81");
		RequestContext requestContext = RequestContext.getRequestContext();
		requestContext.setAuth(auth);
		flickr.setAuth(auth);
	}
	
	@Override
	public String savePhoto(InputStream photo, String title) throws Exception {
		connect();
		uploadMetaData.setTitle(title);
		String photoId = flickr.getUploader().upload(photo, uploadMetaData);
		return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
	}

	public void auth() {
		flickr = new Flickr(apiKey, sharedSecret, new REST());

		AuthInterface authInterface = flickr.getAuthInterface();

		Token token = authInterface.getRequestToken();
		System.out.println("token : " + token);
		
		String url = authInterface.getAuthorizationUrl(token, Permission.DELETE);
		System.out.println("Follow this url to authorise yourself on flickr");
		System.out.println(url);
		System.out.println("Past in the token it give you : ");
		System.out.println(">>");
		
		String tokenKey = JOptionPane.showInputDialog(null);
		
		Token requestToken = authInterface.getAccessToken(token, new Verifier(tokenKey));
		System.out.println("Authentification succes");
		
		Auth auth = null;
		try {
			auth = authInterface.checkToken(requestToken);
		} catch (FlickrException e) {
			e.printStackTrace();
		}
		
		System.out.println("Token : " + requestToken.getToken());
		System.out.println("Secret : " + requestToken.getSecret());
		System.out.println("nsid : " + auth.getUser().getId());
		System.out.println("Realname : " + auth.getUser().getRealName());
		System.out.println("Username : " + auth.getUser().getUsername());
		System.out.println("Permission : " + auth.getPermission().getType());

	}

}
