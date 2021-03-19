<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script type="text/javascript">
		window.onload=function(){
			let chk='${check}';
			if(chk==='1'){
				alert('회원가입 성공');
			}else if(chk==='2'){
				alert('로그인 실패');
			}
		};
	</script>
</head>
<body>
<h1>
<!--${mb.id}<br>
${mb.pw}<br> -->
Home.jsp - 로그인 페이지 - ${check}
</h1>

<form action="access" method="post">
	<table border="1">
		<tr>
			<td colspan="2" align="center" bgcolor="skyblue">로그인</td>
		</tr>
		<tr>
			<td><input type="text" name="m_id"></td>
			<td rowspan="2"><button>로그인</button></td>
		</tr>
		<tr>
			<td><input type="password" name="m_pw"></td>
		</tr>
		<tr>
			<td colspan="2" align="center" bgcolor="skyblue">로그인하세요!</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><a href="./joinfrm">회원가입</a></td>
		</tr>
	</table>
</form>

</body>
</html>
