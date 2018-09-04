package com.shenyingchengxun.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.Notes;
public interface NotesDao {
	
	public void addNotes(@Param("no")Notes no);
	
	public List<Notes> getNotesListByHouseid(@Param("houseid")String houseid);
	
	public void delNotesByHouseid(@Param("houseid")String houseid);
	
	public void delNotes(@Param("id")String id);
}
