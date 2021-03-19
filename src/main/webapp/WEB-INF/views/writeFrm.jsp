<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<h3>글쓰기</h3>
<form action="boardwrite" id="frm" method="post" enctype="multipart/form-data"> <!-- encoded방식으로 보내기! get으로 하면 주소 노출이됌 -->
<table border="1">
	<tr>
		<td>제목</td>
		<td><input type="text" id="b_title" name="b_title" style="width:400px;" required></td> <!-- required는 필수데이터라는 뜻 -->
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea rows="20" cols="60" id="b_contents" name="b_contents" required></textarea></td>
	</tr>
	<tr>
		<td>파일첨부</td>
		<td>
			<input type="file" name="files" id="files" multiple> <!-- multiple은 파일 여러개선택할수있음 -->
			<input type="hidden" id="fileCheck" name="fileCheck" value="0">
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="button" onclick="formData()" value="FormData">
			<input type="submit" value="작성완료">
			<input type="reset" id="reset" value="작성취소">
			<input type="button" onclick="location.href='./boardlist'" value="リストに戻る">
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
	//FormData객체 사용 목적
	//1. multipart/form-data를 Ajax(비동기통신)시 무조건 form-data객체 사용(파일 업로드)
	//2. FormData객체는 form의 일부 데이터만 서버로 전송할 때 좋다.
	//-- 서버가 restFul인 경우 ajax를 이용해서 서버로 넘긴다 --> JSON,xml 반환
	function formData(){
		let $obj=$("#files");
		console.log($obj); //jQ객체
		console.dir($obj[0]); //js객체
		console.log($obj[0].files);
		console.log($obj[0].files.length); //2
		console.log($obj[0].files[0]);
		
		//let formData=new FormData($('#frm')); //js지원 객체 !에러!
		//let formData=new FormData(document.getElementById("frm")); //js지원 객체, !OK!
		//console.log(formData.get("b_title"));
		//console.log(formData.getAll("files")); //key는 name속성명, get은 1개 파일만 출력됨. getAll 파일리스트 출력
		
		//폼의 일부데이터만 저장 append 꺼낼때는 get
		let formData = new FormData();
		formData.append("b_title",$('#b_title').val()); //키 , 값
		formData.append("b_contents",$('#b_contents').val()); //키 , 값
		formData.append("fileCheck",$('#fileCheck').val()); //키 , 값
		
		let files = $obj[0].files;
		for(let i = 0 ; i < files.length ; i++ ){
			formData.append("files", files[i]); //Map과 달리 속성(키) 같아도 쌓여서 모두 저장
		}
		console.log(formData.get("b_title"));
		console.log(formData.getAll("files")); 
		
		$.ajax({
			type: 'post', //폼데이터는 반드시 post
			url: 'rest/boardwrite',
			data: formData,
			processData: false, //urlencoded(쿼리스트링 형식)처리 금지
			contentType: false, //multipart의 경우 false
			dataType: 'json', //rest컨트롤러 이용
			success:function(data){
				console.log(data);
			},error:function(err){
				console.log(err);
			}
		});
	}
	$('#reset').on('click',function(){
		console.log('reset');
		$('#fileCheck').val(0);
	});
	$('#files').on('change',function(){
		console.dir(this); //file객체
		console.dir(this.value); //선택된file명
		if($(this).val()==''){ //파일취소클릭
			console.log('empty');
			$('#fileCheck').val(0); //첨부가 안됨
		}else{
			console.log("not empty");
			$('#fileCheck').val(1); //첨부됨
		}
		console.log($('#fileCheck').val()); //change 1,0
	}); //change
</script>
</body>
</html>