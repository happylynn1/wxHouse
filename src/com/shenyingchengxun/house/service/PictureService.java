package com.shenyingchengxun.house.service;

import java.util.List;

import com.shenyingchengxun.house.bean.Picture;
import com.shenyingchengxun.house.bean.Video;

public interface PictureService {
	
	public List<Picture> getPictureByOpenid(String openid);

	public List<Picture> getPictureByHouseid(String houseid);

	public void addPictureByOpenid(Picture picture);

	public void upPictureByOpenid(Picture picture);

	public void delPicture(String filename,String openid);

	public void delPicture(String filename,String openid,String houseid);

	public int addVideoInfo(Video video);

	public List<String> getFileNameByOpenid(String openid, String storeid);

	public void delVideoByOpenid(String openid, String storeid);

	public int delVideoByFilename(String filename);

	public int upPictureByPicNameAndHouseid(String houseid,String picPath);

	public int delVideoByFilenameAndHouseid(String filename,String houseid);
	
	public List<Video> getVideoByHouseid(String houseid);
	
}
