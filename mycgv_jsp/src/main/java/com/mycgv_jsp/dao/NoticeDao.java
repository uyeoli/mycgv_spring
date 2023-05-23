package com.mycgv_jsp.dao;

import java.util.ArrayList;

import com.mycgv_jsp.vo.NoticeVo;

public class NoticeDao extends DBConn{
	
	/* ��ü ����Ʈ */
	public ArrayList<NoticeVo> select(int startCount, int endCount){
		
		ArrayList<NoticeVo> list = new ArrayList<NoticeVo>();
		try{
			//String sql = "SELECT ROWNUM RNO, NO, TITLE, to_char(NDATE,'yyyy-mm-dd'), HITS "
			//			+ " FROM (SELECT * FROM CGV_NOTICE ORDER BY NDATE DESC)";
			String sql = "select rno, nid, ntitle, ncontent, nhits, ndate\r\n" + 
					"from (select rownum rno, nid, ntitle, ncontent, nhits, to_char(ndate, 'yyyy-mm-dd') ndate\r\n" + 
					"          from (select nid, ntitle, ncontent, nhits, ndate from mycgv_notice order by ndate desc))\r\n" + 
					"                where rno between ? and ?";
			
			getPreparedStatement(sql);
			pstmt.setInt(1, startCount);
			pstmt.setInt(2, endCount);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				NoticeVo vo = new NoticeVo();
				vo.setRno(rs.getInt(1));
				vo.setNid(rs.getString(2));
				vo.setNtitle(rs.getString(3));
				vo.setNcontent(rs.getString(4));
				vo.setNhits(rs.getInt(5));
				vo.setNdate(rs.getString(5));
				list.add(vo);
			}
			
		}catch(Exception e){ 
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	/*
	 * SELECT - �������� ��ü ����Ʈ
	 */
	public ArrayList<NoticeVo> select() {
		ArrayList<NoticeVo> list = new ArrayList<NoticeVo>();
		String sql = "SELECT ROWNUM RNO, NID, NTITLE, NCONTENT, NHITS, TO_CHAR(NDATE,'YYYY-MM-DD') NDATE\r\n" + 
				" FROM (SELECT * FROM MYCGV_NOTICE ORDER BY NID DESC)";
		getPreparedStatement(sql);
		try {
			rs = pstmt.executeQuery();
			while(rs.next()) {
				NoticeVo noticeVo = new NoticeVo();
				noticeVo.setRno(rs.getInt(1));
				noticeVo.setNid(rs.getString(2));
				noticeVo.setNtitle(rs.getString(3));
				noticeVo.setNcontent(rs.getString(4));
				noticeVo.setNhits(rs.getInt(5));
				noticeVo.setNdate(rs.getString(6));
				list.add(noticeVo);
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
	
	
	/*
	 * �������� �۾��� ó��
	 */
	public int insert(NoticeVo noticeVo) {
		int result = 0;
		String sql = "INSERT INTO MYCGV_NOTICE VALUES('n_'||ltrim(to_char(sequ_mycgv_notice.nextval, '0000')),\r\n" + 
				"?,?,0,SYSDATE)";
		getPreparedStatement(sql);
		try {
			pstmt.setString(1, noticeVo.getNtitle());
			pstmt.setString(2, noticeVo.getNcontent());
			
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public NoticeVo select(String nid) {
		NoticeVo noticeVo = new NoticeVo();
		String sql = "SELECT NID, NTITLE, NCONTENT, NHITS, TO_CHAR(NDATE,'YYYY-MM-DD') NDATE FROM MYCGV_NOTICE WHERE NID = ?";
		getPreparedStatement(sql);
		try {
			pstmt.setString(1, nid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				noticeVo.setNid(rs.getString(1));
				noticeVo.setNtitle(rs.getString(2));
				noticeVo.setNcontent(rs.getString(3));
				noticeVo.setNhits(rs.getInt(4));
				noticeVo.setNdate(rs.getString(5));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return noticeVo;
	}
	
	/*
	 * �������� ��ȸ�� ó��
	 */
	public void updateHits(String nid) {
		String sql = "update mycgv_notice set nhits = nhits + 1 where nid = ?";
		getPreparedStatement(sql);
		try {
			pstmt.setString(1, nid);
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * �������� ���� ó��
	 */
	public int update(NoticeVo noticeVo) {
		int result = 0;
		String sql = "update mycgv_notice set ntitle = ?, ncontent = ? where nid = ?";
		getPreparedStatement(sql);
		try {
			pstmt.setString(1, noticeVo.getNtitle());
			pstmt.setString(2, noticeVo.getNcontent());
			pstmt.setString(3, noticeVo.getNid());
			
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * �������� ����ó��
	 */
	public int delete(String nid) {
		int result = 0;
		String sql = "delete from mycgv_notice where nid = ?";
		getPreparedStatement(sql);
		try {
			pstmt.setString(1, nid);
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
