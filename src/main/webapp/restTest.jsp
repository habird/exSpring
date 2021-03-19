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
<h1>restTest.jsp</h1>
<script type="text/javascript">
$.ajax({
	method:'post', //get(select)또는post(_method:delete,put,patch), type속성이 안되는 경우가 있음
	//url:'member/getSample',
	//url:'member/patch', //test , put
	url:'member/manager/30',
	data:{_method:'patch',num:10}, //delete,put,patch
	//method:'delete',
	dataType:'html', //xml,json,html
	success: function(data){
		console.log(data);
	},
	error: function(err){
		console.log(err);
	}
}); //ajax End
</script>
</body>
</html>