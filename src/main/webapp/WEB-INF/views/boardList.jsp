<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"   %>     

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
html, body {
	height: 100%;
	margin: 0
}

#articleView_layer {
	display: none;
	position: fixed;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%
}

#articleView_layer.open {
	display: block;
	color: red;
}

#articleView_layer #bg_layer {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #000;
	opacity: .5;
	filter: alpha(opacity = 50);
	z-index: 100
}

#contents_layer {
	position: absolute;
	top: 40%;
	left: 40%;
	width: 400px;
	height: 400px;
	margin: -150px 0 0 -194px;
	padding: 28px 28px 0 28px;
	border: 2px solid #555;
	background: #fff;
	font-size: 12px;
	z-index: 200;
	color: #767676;
	line-height: normal;
	white-space: normal;
	overflow: scroll
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="resources/js/jquery.serializeObject.js"></script>

<script type="text/javascript">
   /* console.dir(${bList2});  */
   function articleView(num){
	   $("#articleView_layer").addClass('open');
	   $.ajax({
		   type : 'get',
		   url : 'contents', //-> 컨트롤러 -> mm -> 컨트롤러 -> ajaxContents의 html을 가지고 -> ajax로 돌아옴 done(data) -> 원하는 레이아웃에 html데이타 출력
		   data : {bNum : num},
		   dataType : 'html' //ajaxContents.jsp에 상세 내용을 출력 후 리턴 //html이 기본값 싹 가지고 오겠다
		   //done 혹은 success
	
	   }).done((data)=>$('#contents_layer').html(data)) //console.log(data))
	   	 .fail((err)=>console.log(err)); //ajax End
		   
/* 	   }).done(function(data){
		 console.log(data);  
	   }).fail(function(arr){
		 console.log(data);		 
	   }); //ajax End
 */   }
	   
	  $(function(){
		  const $window = $("#articleView_layer");
		  $window.find("#bg_layer").on('mousedown',function(evt){
			  //console.log(evt);
			  $window.removeClass('open');
		  })
		  //esc키로 모달창 닫기
		  $(document).keydown(function(evt){
			  //console.log(evt); //콘솔로 찍었을 때 esc의 키 코드는 27번!
			  if(evt.keyCode!=27) 	  return; //의미 없으니까!
			  else if($window.hasClass('open')){ //모달창이 떠있을 때만
				  $window.removeClass('open'); // 떠있는 것을 제거한다.
			  } 
		  }); //keydown End
		  
		  //삭제된 글번호 출력
		  if('${bNum}'===''){
			  return;
		  }else if(parseInt('${bNum}')>0){ //문자열을 숫자로 바꿔서 0보다 크다면
			  alert('${bNum}번 글을 삭제했습니다.');
		  }
	  }); //ready end
</script>
</head>
<body>
${msg}
<h3>게시판 리스트</h3>
<div>
아이디 : ${mb.m_id}<br>
이름 : ${mb.m_name}<br>
등급 : ${mb.g_name}<br>
포인트 : ${mb.m_point}<br>
</div>

<c:if test="${!empty id}">
	<div align="right">
		<form name="logoutFrm" action="logout" method="post">
			<a href="javascript:document.logoutFrm.submit()">로그아웃</a>
		</form>
	</div>
</c:if>
<!-- 회원정보 출력 : MINFO view -->
   <table>
      <tr bgcolor="orange" height="30">
         <th width="100">번호</th>
         <th width="100">제목</th>
         <th width="100">작성자</th>
         <th width="100">작성일</th>
         <th width="100">조회수</th>
      </tr>
      
      <c:forEach var="board" items="${bList}">
         <tr height="20">
            <td align="center">${board.b_num}</td>
            <td align="center"><a href="#" onclick="articleView(${board.b_num})">${board.b_title}</a></td>
            <td align="center">${board.b_id}</td>
            <td align="center">${board.b_date}</td>
            <td align="center">${board.b_views}</td>
         </tr>
      </c:forEach>
      
   </table>
   <!-- 글쓰기 -->
   <form action="writefrm">
   		<button>글쓰기</button>
   
   </form>
   
   
   <!-- 페이징 -->
   <div align="center">
	   ${paging}
   </div>

	<!-- 모달(Modal) -->
	<div id="articleView_layer">
		<div id="bg_layer"></div>
		<div id="contents_layer"></div>
	</div>
	
	<h4>test</h4>
	<form action="test">
		컬럼명:<input type="text" name="cName"><br>
		검색:<input type="text" name="search"><br>
		<button>검색</button>
	</form>

	<h4>testmap</h4>
	<form action="testmap">
		컬럼명:<input type="text" name="cName"><br>
		검색:<input type="text" name="search"><br>
		<button>검색</button>
	</form>
</body>
</html>