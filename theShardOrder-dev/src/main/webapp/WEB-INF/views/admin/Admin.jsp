<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<link rel="stylesheet" href="css/common.css" />
<script src="js/kakaoLogin.js" type="text/javascript"></script>
<script src="js/loginCheck.js" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	
	
	<h2>관리자 페이지</h2>
	
	<ul>
		<li><a href="">회원관리</a></li>
		<li><a href="">제품 관리</a></li>
		<li><a href="">리뷰 & 문의 관리</a></li>
	</ul>	
	
	<jsp:include page="../footer.jsp" />
</body>
</html>