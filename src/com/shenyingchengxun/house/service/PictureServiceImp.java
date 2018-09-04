package com.shenyingchengxun.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.house.bean.Picture;
import com.shenyingchengxun.house.bean.Video;
import com.shenyingchengxun.house.dao.PictureDao;

@Service("PictureServiceImp")
public class PictureServiceImp implements PictureService{

	@Autowired PictureDao pictureDao;
	public List<Picture> getPictureByOpenid(String openid) {
		// TODO Auto-generated method stub
		return pictureDao.getPictureByOpenid(openid);
	}

	public void addPictureByOpenid(Picture picture) {
		// TODO Auto-generated method stub
		pictureDao.addPictureByOpenid(picture);
	}

	public void upPictureByOpenid(Picture picture) {
		// TODO Auto-generated method stub
		pictureDao.upPictureByOpenid(picture);
	}

	public void delPicture(String filename, String openid) {
		// TODO Auto-generated method stub
		pictureDao.delPicture(filename,openid);
	}
	public void delPicture(String filename, String openid,String houseid) {
		// TODO Auto-generated method stub
		pictureDao.delPictureById(filename,openid,houseid);
	}

	public List<Picture> getPictureByHouseid(String houseid) {
		// TODO Auto-generated method stub
		return pictureDao.getPictureByHouseid(houseid);
	}

	@Override
	public int addVideoInfo(Video video) {
		// TODO Auto-generated method stub
		return pictureDao.addVideoInfo(video);
	}

	@Override
	public List<String> getFileNameByOpenid(String openid, String storeid) {
		// TODO Auto-generated method stub
		return pictureDao.getFileNameByOpenid(openid,storeid);
	}

	@Override
	public void delVideoByOpenid(String openid, String storeid) {
		// TODO Auto-generated method stub
		pictureDao.delVideoByOpenid(openid,storeid);
	}

	@Override
	public int delVideoByFilename(String filename) {
		// TODO Auto-generated method stub
		return pictureDao.delVideoByFilename(filename);
	}

	@Override
	public List<Video> getVideoByHouseid(String houseid) {
		// TODO Auto-generated method stub
		return pictureDao.getVideoByHouseid(houseid);
	}

	@Override
	public int delVideoByFilenameAndHouseid(String filename, String houseid) {
		// TODO Auto-generated method stub
		return pictureDao.delVideoByFilenameAndHouseid(filename, houseid);
	}

	@Override
	public int upPictureByPicNameAndHouseid(String houseid, String picPath) {
		// TODO Auto-generated method stub
		pictureDao.upPictureByHouseid(houseid);
		return pictureDao.upPictureByPicNameAndHouseid(houseid,picPath);
	}
}
