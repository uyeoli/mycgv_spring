package com.mycgv_jsp.service;

import java.util.ArrayList;

import com.mycgv_jsp.vo.NoticeVo;

public interface NoticeService {
	ArrayList<NoticeVo> getList(int startCount, int endCount);
	int getTotalRowCount();
	void getUpdateHits(String nid);
	int getInsert(NoticeVo noticeVo);
	NoticeVo getSelect(String nid);
	int getUpdate(NoticeVo noticeVo);
	int getDelete(String nid);
}
