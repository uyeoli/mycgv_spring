package com.mycgv_jsp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycgv_jsp.dao.NoticeDao;
import com.mycgv_jsp.vo.NoticeVo;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
	//NoticeDao noticeDao = new NoticeDao();
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public ArrayList<NoticeVo> getList(int startCount, int endCount){
		ArrayList<NoticeVo> rlist = new ArrayList<NoticeVo>();
		List<Object> list = noticeDao.select(startCount, endCount);
		for(Object obj : list) {
			NoticeVo noticeVo = (NoticeVo)obj;
			rlist.add(noticeVo);
		}
		return rlist;
	};
	@Override
	public void getUpdateHits(String nid) {
		noticeDao.updateHits(nid);
	};
	@Override
	public int getInsert(NoticeVo noticeVo) {
		return noticeDao.insert(noticeVo);
	};
	@Override
	public NoticeVo getSelect(String nid) {
		return noticeDao.select(nid);
	};
	@Override
	public int getUpdate(NoticeVo noticeVo) {
		return noticeDao.update(noticeVo);
	};
	@Override
	public int getDelete(String nid) {
		return noticeDao.delete(nid);
		
	};
}
