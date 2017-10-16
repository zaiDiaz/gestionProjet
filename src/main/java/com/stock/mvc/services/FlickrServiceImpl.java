package com.stock.mvc.services;

import java.io.InputStream;

import com.stock.mvc.dao.IFlickrDao;

public class FlickrServiceImpl implements IflickrService {
	
	private IFlickrDao dao;
	
	public void setDao(IFlickrDao dao) {
		this.dao = dao;
	}

	@Override
	public String savePhoto(InputStream stream, String fileName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
