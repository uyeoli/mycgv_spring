package com.mycgv_jsp.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.BoardVo;

@Service
public class BoardServiceImpl implements BoardService{
	
	private BoardDao boardDao = new BoardDao();
	
	@Override
	public ArrayList<BoardVo> getList(int startCount, int endCount){
		return boardDao.select(startCount, endCount);
	}
	
	@Override
	public int getTotalRowCount() {
		return boardDao.totalRowCount();
	}
	
	@Override
	public BoardVo getContent(String bid) {
		return boardDao.select(bid);
	}
	
	@Override
	public void getUpdateHits(String bid) {
		boardDao.updateHits(bid);
	}
	
	@Override
	public int boardInsert(BoardVo boardVo) {
		return boardDao.insert(boardVo);
	}
	
	@Override
	public BoardVo boardSelect(String bid) {
		return boardDao.select(bid);
	}
	
	@Override
	public int boardUpdate(BoardVo boardVo) {
		return boardDao.update(boardVo);
	}
	
	@Override
	public int boardDelete(String bid) {
		return boardDao.delete(bid);
	}
	
}
