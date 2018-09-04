package com.shenyingchengxun.house.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.dao.HouseTagDao;

@Service("HouseTagServiceImp")
public class HouseTagServiceImp implements HouseTagService{

	@Autowired HouseTagDao houseTagDao;

	public List<Map<String,Object>> getHouseTagList(String type) {
		// TODO Auto-generated method stub
		List<Map<String,String>> list = houseTagDao.getHouseTagList(type);
		List<Map<String,Object>> resultList = new  ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			String group = list.get(i).get("label_group");
			String label = list.get(i).get("label");
			String name = list.get(i).get("label_name");
			String islabel = list.get(i).get("islabel");
			boolean flag =true;
			for (int j = 0; j < resultList.size(); j++) {
				String group2 = (String)resultList.get(j).get("group");
				if(group2.equals(group)){
					@SuppressWarnings("unchecked")
					List<Map<String,String>> lists =(List<Map<String,String>>)resultList.get(j).get("list");
					Map<String,String>  maps = new HashMap<String,String>();
					maps.put("label", label);
					maps.put("name", name);
					maps.put("islabel", islabel);
					lists.add(maps);
					flag =false;
					break;
				}
			}
			if (flag) {
				Map<String,Object>  map = new HashMap<String,Object>();
				map.put("group", group);
				List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
				Map<String,String>  maps = new HashMap<String,String>();
				maps.put("label", label);
				maps.put("name", name);
				maps.put("islabel", islabel);
				lists.add(maps);
				map.put("list", lists);
				resultList.add(map);
			}
		}
		return resultList;
	}

	public List<String> getLikeTags(List<String> tagList,HouseSource hs) {
		// TODO Auto-generated method stub
		List<String> resultList = new ArrayList<String>();
		Field[] fields = hs.getClass().getDeclaredFields();
		for(int i = 0 , len = fields.length; i < len; i++) {  
			String name = fields[i].getName();
			Method m;
			try {
				m = hs.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
				String value = (String) m.invoke(hs);
				if(value!=null&&tagList.contains(value)){
					resultList.add(value);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		return resultList;
	}
}
