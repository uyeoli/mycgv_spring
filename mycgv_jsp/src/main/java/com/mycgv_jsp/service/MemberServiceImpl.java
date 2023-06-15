package com.mycgv_jsp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycgv_jsp.dao.MemberDao;
import com.mycgv_jsp.vo.MemberVo;
import com.mycgv_jsp.vo.SessionVo;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDao memberDao;
	
	
	@Override
	public SessionVo getLoginResult(MemberVo memberVo) {
		return memberDao.loginCheck(memberVo);
	};
	@Override
	public String getIdCheckResult(String id) {
		int result = memberDao.idCheck(id);
		return String.valueOf(result);
	}
	@Override
	public int getJoinResult(MemberVo memberVo) {
		return memberDao.insert(memberVo);
	}
	@Override
	public ArrayList<MemberVo> getList(int startCount, int endCount) {
		ArrayList<MemberVo> rlist = new ArrayList<MemberVo>();
		List<Object> list = memberDao.select(startCount, endCount);
		for(Object obj : list) {
			MemberVo memberVo = (MemberVo)obj;
			rlist.add(memberVo);
		}
		return rlist;
	}

}
