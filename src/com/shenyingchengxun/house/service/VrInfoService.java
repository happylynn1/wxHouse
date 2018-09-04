package com.shenyingchengxun.house.service;

import java.util.List;

import com.shenyingchengxun.house.bean.VrInfo;

public interface VrInfoService {
	
	public List<VrInfo> getVrlist(String phone,String comm,String area,String room,String towards);
	
	public VrInfo getMyVr(String vrno);

}
