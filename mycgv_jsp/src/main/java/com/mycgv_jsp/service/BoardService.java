package com.mycgv_jsp.service;

import java.util.ArrayList;

import com.mycgv_jsp.vo.BoardVo;

public interface BoardService {
	public ArrayList<BoardVo> getList(int startCount, int endCount);
	public int getTotalRowCount();
	public BoardVo getContent(String bid);
	public void getUpdateHits(String bid);
	public int boardInsert(BoardVo boardVo);
	public BoardVo boardSelect(String bid);
	public int boardUpdate(BoardVo boardVo);
	public int boardDelete(String bid);
}
