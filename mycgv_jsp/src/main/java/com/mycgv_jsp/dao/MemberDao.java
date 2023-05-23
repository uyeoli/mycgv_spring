package com.mycgv_jsp.dao;

import java.util.ArrayList;

import com.mycgv_jsp.vo.MemberVo;

public class MemberDao extends DBConn{
	
	public ArrayList<MemberVo> select(int startCount, int endCount) {
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		String sql = "select rno,id,name, mdate, grade\r\n" + 
				" from (select rownum rno, id, name, to_char(mdate, 'yyyy-mm-dd') mdate, grade \r\n" + 
				" from (select id, name, mdate, grade from mycgv_member order by mdate desc))\r\n" + 
				" where rno between ? and ?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setInt(1, startCount);
			pstmt.setInt(2, endCount);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberVo memberVo = new MemberVo();
				memberVo.setRno(rs.getInt(1));
				memberVo.setId(rs.getString(2));
				memberVo.setName(rs.getString(3));
				memberVo.setMdate(rs.getString(4));
				memberVo.setGrade(rs.getString(5));
				list.add(memberVo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	public int totalRowCount() {
		int count = 0;
		String sql = "select count(*) from mycgv_notice";
		getPreparedStatement(sql);
		
		try {
			rs = pstmt.executeQuery();
			while(rs.next()) {				
				count = rs.getInt(1);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;		
	}	
	
	
	/* loginCheck - 로그인체크 */
	public int loginCheck(MemberVo memberVo) {
		int result = 0;
		String sql = "select count(*) from mycgv_member where id = ? and pass = ?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, memberVo.getId());
			pstmt.setString(2, memberVo.getPass());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	/*
	 idCheck - 아이디 중복체크
	 */
	public int idCheck(String id) {
		int result = 0;
		String sql = "select count(*) from mycgv_member where id = ?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	// insert - 회원가입
	public int insert(MemberVo memberVo) {
		int result = 0;
		String sql = "insert into mycgv_member (id,pass,name,gender,email,addr,tel,pnumber,hobbylist,intro,mdate,grade) " 
					+ " values(?,?,?,?,?,?,?,?,?,?,sysdate, 'GOLD')";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, memberVo.getId());
			pstmt.setString(2, memberVo.getPass());
			pstmt.setString(3, memberVo.getName());
			pstmt.setString(4, memberVo.getGender());
			pstmt.setString(5, memberVo.getEmail());
			pstmt.setString(6, memberVo.getAddr());
			pstmt.setString(7, memberVo.getTel());
			pstmt.setString(8, memberVo.getPnumber());
			pstmt.setString(9, memberVo.getHobbyList());
			pstmt.setString(10, memberVo.getIntro());
			result = pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	//select - 관리자
	public ArrayList<MemberVo> select() {
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		String sql = "select rownum rno, id, name, to_char(mdate,'yyyy-mm-dd') mdate, grade \r\n" + 
				"from (select id, name, mdate, grade from mycgv_member order by mdate desc)";
		getPreparedStatement(sql);
		
		try {
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVo memberVo = new MemberVo();
				memberVo.setRno(rs.getInt(1));
				memberVo.setId(rs.getString(2));
				memberVo.setName(rs.getString(3));
				memberVo.setMdate(rs.getString(4));
				memberVo.setGrade(rs.getString(5));
				list.add(memberVo);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
