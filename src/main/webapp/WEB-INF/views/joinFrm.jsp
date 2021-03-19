<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>joinFrm.jsp - 회원가입 페이지</h1>
<form action="memberjoin" name="joinFrm" method="post" onsubmit="return check()">
	<table>
		<tr>
			<td colspan="2" class="subject">회원가입</td>
		</tr>
		<tr>
			<td width="100">ID</td>
			<td><input type="text" id="id" name="m_id">
				<input type="button" id="checkId" value="중복검사">
				<div id="result"></div>
			</td>
		</tr>
		<tr>
			<td width="100">PW</td>
			<td><input type="text" id="pw" name="m_pw"></td>
		</tr>
		<tr>
			<td width="100">NAME</td>
			<td><input type="text" id="name" name="m_name"></td>
		</tr>
		<tr>
			<td width="100">BIRTH</td>
			<td><input type="text" id="birth" name="m_birth"></td>
		</tr>
		<tr>
			<td width="100">ADDR</td>
			<td><input type="text" id="addr" name="m_addr"></td>
		</tr>
		<tr>
			<td colspan="2" class="subject"><input type="submit" value="회원가입하기"></td>
		</tr>
	</table>
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
$('#checkId').on('click',function(){
	if($('#id').val() != ''){
		$.ajax({
			type: 'get',
			url: 'member/userid',
			data: 'm_id='+$('#id').val(), //{m_id:$('#id').val()} //가져갈 데이터
			//header:{'Content-type':"app"}
			dataType: 'html', //사용할 수 있는 아이디입니다.
			success: function(data){
				$('#result').html(data).css('color','blue');
			},
			error: function(err){//,status){
				$('#result').html(err.responseText).css('color','red');
				console.log("err.status=", err.status);
			}
		}); //ajax End
	} //if End
}); // on End

function check(){
	let frm=document.joinFrm;
	let length=frm.length-1;
	
	for(let i=0;i<length;i++){
		if(frm[i].value==""){
			alert(frm[i].id + "를 입력하세요.");
			frm[i].focus
			return false;
		}
	}
	return true; //서버로 전송
}
</script>
</body>
</html>