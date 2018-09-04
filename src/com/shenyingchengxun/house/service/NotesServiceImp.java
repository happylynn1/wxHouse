package com.shenyingchengxun.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.house.bean.Notes;
import com.shenyingchengxun.house.dao.NotesDao;

@Service("NotesServiceImp")
public class NotesServiceImp implements NotesService{

	@Autowired NotesDao notesDao;

	@Override
	public List<Notes> getNotesListByHouseid(String houseid) {
		// TODO 自动生成的方法存根
		return notesDao.getNotesListByHouseid(houseid);
	}

	@Override
	public String addNotes(String houseid, String notes) {
		// TODO 自动生成的方法存根
		Notes a = new Notes(houseid,notes);
		notesDao.addNotes(a);
		return a.getId();
	}

	@Override
	public void delNotes(String id) {
		// TODO Auto-generated method stub
		notesDao.delNotes(id);
	}
}
