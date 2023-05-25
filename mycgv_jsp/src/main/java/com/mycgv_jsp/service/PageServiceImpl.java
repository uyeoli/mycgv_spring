package com.mycgv_jsp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class PageServiceImpl {
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BoardService boardService;

	public Map<String,Integer> getPageResult(String page, String serviceName) {
		Map<String, Integer> param = new HashMap<String, Integer>();

		//페이징 처리 - startCount, endCount 구하기
		int startCount = 0;
		int endCount = 0;
		int pageSize = 5;	//한페이지당 게시물 수
		int reqPage = 1;	//요청페이지	
		int pageCount = 1;	//전체 페이지 수
		int dbCount = 0;	//DB에서 가져온 전체 행수
		
		if(serviceName.equals("notice")) {
			//매개변수 serviceType을 noticeService로 변환
			//noticeService = (NoticeService)serviceType;
			dbCount = noticeService.getTotalRowCount();
			pageSize = 3;
		} else if(serviceName.equals("board")) {
			dbCount = boardService.getTotalRowCount();
			pageSize = 5;
		} else if(serviceName.equals("member")) {
			dbCount = memberService.getTotalRowCount();
			pageSize = 10;
		}
		
		//총 페이지 수 계산
		if(dbCount % pageSize == 0){
			pageCount = dbCount/pageSize;
		}else{
			pageCount = dbCount/pageSize+1;
		}

		//요청 페이지 계산
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
