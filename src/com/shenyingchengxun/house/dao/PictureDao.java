package com.shenyingchengxun.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.Picture;
import com.shenyingchengxun.house.bean.Video;
public interface PictureDao {
	
	public List<Picture> getPictureByOpenid(@Param("openid")String openid);

	public List<Picture> getPictureByHouseid(@Param("houseid")String houseid);

	public void addPictureByOpenid(@Param("picture")Picture picture);

	public void upPictureByOpenid(@Param("picture")Picture picture);

	public void upPictureByPicName(@Param("picPath")String picPath,@Param("openid")String openid);

	public void delPicture(@Param("filename")String filename,@Param("openid")String openid);

	public void delPictureById(@Param("filename")String filename,@Param("openid")String openid,@Param("houseid")String houseid);

	public void delPictureByHouseid(@Param("houseid")String houseid);

	public int addVideoInfo(@Param("video")Video video);

	public List<String> getFileNameByOpenid(@Param("openid")String openid, @Param("storeid")String storeid);

	public void delVideoByOpenid(@Param("openid")String openid, @Param("storeid")String storeid);

	public int delVideoByFilename(@Param("filename")String filename);

	public void upPictureByHouseid(@Param("houseid")String houseid);
	
	public int upPictureByPicNameAndHouseid(@Param("houseid")String houseid,@Param("picPath")String picPath);

	public int delVideoByFilenameAndHouseid(@Param("filename")String filename,@Param("houseid")String houseid);

	public String getVideoPathByOpenid(@Param("openid")String openid, @Param("storeid")String storeid);

	public void upVideoByOpenid(@Param("openid")String openid, @Param("storeid")String storeid, @Param("houseid")String houseid);

	public List<Video> getVideoByHouseid(@Param("houseid")String houseid);
}
