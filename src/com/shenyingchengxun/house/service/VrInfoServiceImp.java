package com.shenyingchengxun.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.house.bean.VrInfo;
import com.shenyingchengxun.house.dao.VrInfoDao;

@Service("VrInfoServiceImp")
public class VrInfoServiceImp implements VrInfoService{

	@Autowired VrInfoDao vrInfoDao;
	
	public List<VrInfo> getVrlist(String phone, String comm, String area,
			String room, String towards) {
		// TODO Auto-generated method stub
		return vrInfoDao.getVrlist(phone,comm,area,room,towards);
	}

	@Override
	public VrInfo getMyVr(String vrno) {
		// TODO Auto-generated method stub
		return vrInfoDao.getMyVr(vrno);
	}
	
}
