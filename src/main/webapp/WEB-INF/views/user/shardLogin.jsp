<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/login.css" />
<script src="/resources/js/loginCheck.js" type="text/javascript"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&family=Noto+Sans:ital,wght@0,200;1,300&display=swap"
	rel="stylesheet">
<script>
	var result = "${result}";
	
	if(result == "-1"){
		alert("아이디 또는 비밀번호가 맞지 않습니다.");
	}
	
	if(result == "noAdmin"){
		alert("당신은 관리자가 아닙니다.");
	}
	
	if(result == "success"){
		alert("성공적으로 회원가입이 완료되었습니다.");
	}else if(result == "faild"){
		alert("회원가입 도중 에러가 발생했습니다.");
	}
	
</script>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>

	<!-- 회원가입을 성공적으로 마쳤을 때 login페이지로 이동하면서 환영한다는 경고창을 띄우고 로그인을 할 수 있게 한다. -->
	
	<div class="loginWrap">
		<div class="login">
			<ul class="tab">
				<li>회원 로그인</li>
			</ul>
			<p>로그인</p>
			<form action="/shard/login" method="post" id="loginForm" name="loginForm">
				<fieldset>
					<label for="userId" class="id"> <span>아이디</span> <input
						type="text" name="userId" id="userId" />
					</label> <label for="userPwd" class="pwd"> <span>비밀번호</span> <input
						type="password" name="userPwd" id="userPwd" />
					</label>
				</fieldset>
				<div class="findIDandPwd">
					<label for="adminLogin">관리자</label>
					<input type="checkbox" name="adminLogin" id="adminLogin" value="관리자"/>
					<a href="/shard/finduser">아이디/비밀번호 찾기</a>
				</div>
				<a href="javascript:loginCheck()" class="buttonLogin"><span>로그인</span></a>
				<a href="/shard/join" class="buttonJoin"><span>회원가입</span></a>

				<div class="sns_login">
					<p>SNS로그인</p>
					<ul>
						<li><a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=4f8fd0ea2b58d54fc209c75db615c0e7&redirect_uri=http://localhost:8181/shard/login/oauth">
							<img
								src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
								width="222" alt="카카오 로그인 버튼" />
						</a>
							<p id="token-result"></p></li>
					</ul>
				</div>
			</form>
		</div>
	</div>


	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>