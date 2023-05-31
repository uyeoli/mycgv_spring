package com.mycgv_jsp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycgv_jsp.dao.PageDao;

public class PageServiceImpl {
	
//	@Autowired
//	private NoticeService noticeService;
//	@Autowired
//	private MemberService memberService;
//	@Autowired
//	private BoardService boardService;
	@Autowired
	private PageDao pageDao;
	
	public Map<String,Integer> getPageResult(String page, String serviceName) {
		Map<String, Integer> param = new HashMap<String, Integer>();

		//����¡ ó�� - startCount, endCount ���ϱ�
		int startCount = 0;
		int endCount = 0;
		int pageSize = 5;	//���������� �Խù� ��
		int reqPage = 1;	//��û������	
		int pageCount = 1;	//��ü ������ ��
		int dbCount = 0;	//DB���� ������ ��ü ���
		dbCount = pageDao.totalRowCount(serviceName);
		
		if(serviceName.equals("notice")) {
			pageSize = 3;
		} else if(serviceName.equals("board")) {
			pageSize = 5;
		} else if(serviceName.equals("member")) {
			pageSize = 10;
		}
		
		//�� ������ �� ���
		if(dbCount % pageSize == 0){
			pageCount = dbCount/pageSize;
		}else{
			pageCount = dbCount/pageSize+1;
		}

		//��û ������ ���
		if(page != null){
			reqPage = Integer.parseInt(page);
			startCount = (reqPage-1) * pageSize+1; 
			endCount = reqPage *pageSize;
		}else{
			startCount = 1;
			endCount = pageSize;
		}
		
		param.put("startCount", startCount);
		param.put("endCount", endCount);
		param.put("dbCount", dbCount);
		param.put("pageSize", pageSize);
		param.put("maxSize", pageCount);
		param.put("page", reqPage);
		
		return param;
	}
}