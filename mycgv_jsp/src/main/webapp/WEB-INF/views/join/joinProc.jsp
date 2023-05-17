<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.mycgv_jsp.vo.MemberVo, com.mycgv_jsp.dao.MemberDao" %>

<jsp:useBean id = "memberVo" class = "com.mycgv_jsp.vo.MemberVo"></jsp:useBean> <!-- MemberVo객체생성 -> 이름 : memberVo -->
<jsp:setProperty property = "*" name = "memberVo" ></jsp:setProperty> <!-- memberVo에 form으로 넘어온 데이터 set하기 -->
<% 
	MemberDao memberDao = new MemberDao();
	int result = memberDao.insert(memberVo);
	if(result == 1) {
		//alert 창을 띄우려면
		out.write("<script>");
		out.write("alert('회원가입 성공!!');");
		out.write("location.href = 'http://localhost:9000/mycgv_jsp/login/login.jsp'");
		out.write("</script>");
		
		//response.sendRedirect("http://localhost:9000/mycgv_jsp/login/login.jsp?jresult=1"); //로그인창으로 이동
	}
		
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<ul>
		<li><%= memberVo.getId() %></li>
		<li><%= memberVo.getPass() %></li>
		<li><%= memberVo.getName() %></li>
		<li><%= memberVo.getGender() %></li>
		<li><%= memberVo.getEmail() %></li>
		<li><%= memberVo.getAddr() %></li>
		<li><%= memberVo.getTel() %></li>
		<li><%= memberVo.getPnumber() %></li>
		<li><%= memberVo.getHobbyList() %></li>
		<li><%= memberVo.getIntro() %></li>
	</ul>
</body>
</html>