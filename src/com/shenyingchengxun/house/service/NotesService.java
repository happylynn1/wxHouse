package com.shenyingchengxun.house.service;

import java.util.List;

import com.shenyingchengxun.house.bean.Notes;


public interface NotesService {
	
	public String addNotes(String houseid,String notes);
	
	public void delNotes(String id);
	
	public List<Notes> getNotesListByHouseid(String houseid);
}
