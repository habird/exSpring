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
<h1>Board * Reply Contents</h1>
<div>
${delBtn}
</div>
<table>
	<tr height="30">
		<td width="80" bgcolor="yellow" align="center">NUM</td>
		<td colspan="5">&nbsp&nbsp${board.b_num}</td>
	</tr>
	<tr height="30">
		<td width="80" bgcolor="yellow" align="center">WRITER</td>
		<td width="70" align="center">${board.b_id}</td>
		<td width="65" bgcolor="yellow" align="center">DATE</td>
		<td>${board.b_date}</td>
		<td width="65" bgcolor="yellow" align="center">VIEWS</td>
		<td>&nbsp&nbsp${board.b_views}</td>
	</tr>
	<tr height="30">
		<td width="80" bgcolor="yellow" align="center">TITLE</td>
		<td colspan="5" align="center"><b>${board.b_title}</b></td>
	</tr>
	<tr height="30">
		<td width="80" height="130" bgcolor="yellow" align="center">CONTENTS</td>
		<td colspan="5" align="center">${board.b_contents}</td>
	</tr>
</table>

<!-- 댓글 입력 -->
<form id="rFrm" name="rFrm" action="replyInsert">
	<table>
		<tr>
			<td><textarea rows="3" cols="50" name="r_contents" id="r_contents" style="width:280px"></textarea></td>
			<td><input type="button" value="댓글전송" onclick="replyInsert(${board.b_num})" style="width:80px;height:50px"></td>
		</tr>
<%-- 		<tr>
			<th>첨부파일</th>
		</tr>
		<tr>
			<td><c:set var="file" value="${bfList}" />
				<c:if test="${empty files}">
					첨부된 파일이 없습니다.
				</c:if>
				<c:if test="${!empty files}">
					<c:forEach var="file" items="${files}">
						<img src="resources/upload/${file.by_sysname}" width="40">${file.bf_origname}&nbsp;
						
					</c:forEach>
				</c:if>
			</td>
		</tr> --%>
	</table>
</form>

<!-- 첨부파일 가져오기 -->
<table>

<!-- resultMap, collection사용하지 않을 때 -->
<%-- 	<c:forEach var="boardFile" items="${bfList}"> 
		<tr height="20" align="center">
			<td width="100">첨부파일 이름</td>
			<td width="100">
				<a href="./download?sysFileName=${boardFile.bf_sysname}&origFileName=${boardFile.bf_origname}"><!-- 파라미터에는 두개다 가져감 -->
					<img src="resources/upload/${boardFile.bf_sysname}" width="40">
				</a>${boardFile.bf_origname}
			</td>
		</tr>
	</c:forEach> --%>

<!-- rusultMap, colleciton 사용할 떄 -->
	<c:forEach var="boardFile" items="${board.bfList}"> 
		<tr height="20" align="center">
			<td width="100">첨부파일 이름</td>
			<td width="100">
				<a href="./download?sysFileName=${boardFile.bf_sysname}&origFileName=${boardFile.bf_origname}"><!-- 파라미터에는 두개다 가져감 -->
					<img src="resources/upload/${boardFile.bf_sysname}" width="40">
				</a>${boardFile.bf_origname}
			</td>
		</tr>
	</c:forEach>
	
</table>

<!-- 댓글 리스트 출력 -->
<table id="rTable">
	<c:forEach var="reply" items="${rList}">
		<tr height="20" align="center">
			<td width="100">=>&nbsp${reply.r_id}</td>
			<td width="130">${reply.r_contents}</td>
			<td width="120">${reply.r_date}</td>
		</tr>
	</c:forEach>
</table>
<script type="text/javascript"> //자식페이지라서 부모페이지에 등록된 cdn을 사용할수 있다. 반대로 또 자식페이지에 등록하면 오류가 난다.
function replyInsert(bNum){
	console.log("bNum=",bNum);
	let obj=$('#rFrm').serializeObject(); //폼의 모든 데이터를 js객체로 변환 플러그인
	obj.r_bnum=bNum;
	let json=JSON.stringify(obj); //obj --> json
	$.ajax({
		type : 'post', //json일때는 post
		url : 'rest/replyinsert', //+bNum,
		//1.get: 쿼리스트링(주소창 노출)
		//data : {r_bnum : bNum, r_contents : $('#r_contents').val()}, //넘길게 많은 경우 serealizeObject, formdata
		//data : $('#rFrm').serialize(), //jquery 폼 전체 데이터. 대신 이경우에 전송할 bNum이 없다..
		
		//1. obj
		//data : obj,
		//2. json을 서버로 넘김 (서버에서 @RequestBody 받아야 함.)
		data: json,
		
		//쿼리스트링 또는 urlencoded 방식이 아닌 json형식을 서버에 전송할께!
		contentType : "application/json; charset:UTF-8", //기본값
		dataType : 'json',
		success: function(data,status,xhr){ //파라미터 3개까지 찍을수있음
			console.log(data); //출력 확인
			let d = "";
			//d += '<table id="rTable">';
			$.each(data.rList, function(key, value){
				d += '<tr hegith="20" align="center">';
				d += '<td width="100">=>&nbsp'+value.r_id+'</td>';
				d += '<td width="130">'+value.r_contents+'</td>';
				d += '<td width="120">'+value.r_date+'</td>';
				d += '</tr>';
			});
			//d += '</table>';
			$('#rTable').html(d);
			$('#r_contents').val(''); //창에 남아있으니까 비우려고
			$('#r_contents').focusin();
			
			//console.log($('#r_contents').val());
			
		},
		error:(err)=>{console.log(err); console.log(err.responseText);} //responseEntity사용시 출력메세지
	}); //ajax End
} //replayInsert End
</script>
</body>
</html>