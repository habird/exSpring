<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>test.jsp</h1>
<c:forEach var="mb" items="${mList}">
	<!-- map인 경우 컬럼명(대문자)이 키값(대문자)으로 설정됨 -->
	${mb.m_id}, ${mb.m_name}, ${mb.m_point}<br>
</c:forEach>
</body>
</html>