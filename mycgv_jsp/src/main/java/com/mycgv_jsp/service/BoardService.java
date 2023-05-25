package com.mycgv_jsp.service;

import java.util.ArrayList;

import com.mycgv_jsp.vo.BoardVo;

public interface BoardService {
	ArrayList<BoardVo> getList(int startCount, int endCount);
	int getTotalRowCount();
	void getUpdateHits(String bid);
	int getInsert(BoardVo boardVo);
	BoardVo getSelect(String bid);
	int getUpdate(BoardVo boardVo);
	int getDelete(String bid);
}
