function loginCheck(){
	if(document.loginForm.userId.value.length == 0){
		alert("아아디를 입력해주세요");
		loginForm.userId.focus();
		return false;
	}
	if(document.loginForm.userPwd.value == ""){
		alert("비밀번호는 반드시 입력해야 합니다.");
		loginForm.userPwd.focus();
		return false;
	}
	
	document.getElementById("loginForm").submit();
}

$(function(){
	$('.buttonLogin').click(function(e){
		e.preventDefault();
		if($('#adminLogin').is(":checked")){
			$("#loginForm").attr("action", "/shard/adminLogin");
		}

		$("#loginForm").submit();
	})
});