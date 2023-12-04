$(function() {
	// 회원가입 form태그에 있는 input을 focus했을 때 css의 opcity가 0이었던걸 0.1초만에
	// 1로 바꾸고 focus가 out되면 다시 0.3초만에 css의 opacity가 0이 되는 제이쿼리 구문
	$("#joinForm input").focus(function() {
		$(this).animate({ opacity: 1 }, 100);
	}).focusout(function() {
		if ($(this).val().trim() === '') {
			$(this).animate({ opacity: 0 }, 300);
		}
	});

	// 아이디를 입력할 때마다 실시간으로 DB에 있는 userid를 검사해서 밑에 text나오게 하는 구문
	$('#userId').on('input', checkUserId);

	// 비밀번호를 입력할 때마다 실시간으로 비밀번호를 알맞게 썼는지 검사해서 밑에 text나오게 하는 구문
	$('#pwd').on('input', pwdCheck);

	// 비밀번호 확인에 입력할 때마다 비밀번호 입력한 값을 받아서 같은지 확인하는 구문
	$('#pwd_check').on('input', pwdDoubleCheck);

	// "전체 선택" 체크박스의 상태가 변경되었을 때
	$('#all_check').change(function() {
		// "전체 선택" 체크박스의 상태에 따라 다른 체크박스 상태 변경
		var isChecked = $(this).is(':checked');
		$('#playmall, #userinfo').prop('checked', isChecked);
	});

	// 개별 체크박스의 상태가 변경되었을 때
	$('#playmall, #userinfo').change(function() {
		// 개별 체크박스 상태에 따라 "전체 선택" 체크박스 상태 변경
		var playmallChecked = $('#playmall').is(':checked');
		var userinfoChecked = $('#userinfo').is(':checked');
		$('#all_check').prop('checked', playmallChecked && userinfoChecked);
	});
	// "전체 선택" 체크박스의 상태가 변경되었을 때
	$('.all_agree').change(function() {
		// "전체 선택" 체크박스의 상태 가져오기
		var isChecked = $(this).is(':checked');
		// 모든 "전체 선택" 체크박스의 상태 변경
		$('.all_agree').prop('checked', isChecked);
	});
});

// 아이디 ajax로 사용가능한지 체크
function checkUserId() {
	const userId = $('#userId').val();
	const userIdCheckElement = $('.userIdCheck');
	var idPatternEnd = /^[a-z0-9]{4,12}$/;
	

	// Promise 객체를 반환합니다.
	return new Promise(function(resolve, reject) {
		$.ajax({
			type: 'get',
			url: '/shard/idCheck',
			data: { userId: userId },
			success: function(response) {
				var result = response.result;
				if (result === 0) { // 데이터베이스에 사용중인 아이디가 없을 때
					if (idPatternEnd.test(userId)) {
						userIdCheckElement.text("사용 가능한 아이디입니다.").css({ color: "#000" });
						resolve(true); // Promise를 성공 상태로 처리
					} else if (!(idPatternEnd.test(userId))) {
						userIdCheckElement.text("4~12자리(소문자와 숫자를 포함한)입력").css({ color: "#0095ff" });
						resolve(false); // Promise를 성공 상태로 처리
					}
				} else {
					userIdCheckElement.text("사용 중인 아이디입니다.").css({ color: "#0095ff" });
					resolve(false); // Promise를 성공 상태로 처리
				}
			},
			error: function() {
				// 에러 처리
				console.log("에러 발생");
				reject("에러 발생"); // Promise를 실패 상태로 처리
			}
		});
	});
}

// 비밀번호 체크
function pwdCheck() {
	var pwd_check = /^(?=.*[a-z\d].*[a-z\d].*[a-z\d])[a-z\d]{10,}|^(?=.*[a-zA-Z\d@#$%^&+=!])[a-zA-Z\d@#$%^&+=!]{8,16}$/;
	var pwd = $('#pwd').val();
	var pwdCheckSpan1 = $('.pwd_check1');
	pwdCheckSpan1.css({ color: "#0080ff" });
	var check = false;

	if (pwd_check.test(pwd)) {
		pwdCheckSpan1.text("정상입력 입니다.").css({ color: "#000" });
		check = true;
	} else {
		pwdCheckSpan1.text("비밀번호는 영문 대소문자/숫자/특수문자를 2종류 이상 혼용하여 10~16자 또는 3종류 이상 혼용하여 8~16자로 설정하셔야 합니다.");
		check = false;
	}

	if (pwd == "") {
		pwdCheckSpan1.text("비밀번호를 입력해주세요");
		check = false;
	}

	return check;
}

// 비밀번호 확인 체크
function pwdDoubleCheck() {
	var pwd = $('#pwd').val();
	var doublePwd = $('#pwd_check').val();
	var pwdCheckSpan2 = $('.pwd_check2');

	if (pwd == doublePwd) {
		pwdCheckSpan2.text("비밀번호가 일치합니다.").css({ color: "#000" });
		return;
	} else {
		pwdCheckSpan2.text("비밀번호가 일치하지 않습니다.").css({ color: "#0080ff" });
	}
}

// 이메일이 정상적으로 입력이 되었는지 체크
function emailCheck(){
	var email = $('#email').val();
	const emailCheck = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	var check = false;
	
	if(emailCheck.test(email)){
		check = true;	
	}else {
		check = false;
	}
	
	return check;
}

// 주소가 정상적으로 입력되었는지 체크
function addressCheck(){
	var zipCode = $('#postcode').val();
	var userAddr = $("#roadAddress").val() + $("#jibunAddress").val() + $("#extraAddress").val();
	var detailAddr = $("#detailAddress").val();
	var check5 = false;

	if(zipCode == "" && userAddr == "" && detailAddr== ""){
		return false;
	}else {
		check5 = true;
	}

	return check5;
}

function formSubmit() {
	checkUserId().then(function(check) {
		var check2 = pwdCheck(); // 패스워드 체크
		var check3 = $('.all_agree').is(":checked"); // 개인정보 동의 체크
		var check4 = emailCheck(); // 이메일 체크
		var check5 = addressCheck();

		// 휴대폰 인증
		// 생일 인증
		// 약관 인증까지
		
		// 모든 인증이 다 들어가야 한다.

		if (check && check2) {
			if(check4){ // 이메일 체크,
				if (check3) { // 개인정보 동의 체크
					if(check5){ // 회원 주소 체크
						$('#joinForm').submit();
					}else {
						alert("회원님의 주소는 필수입력입니다.");
					}
				}else {
					alert("개인정보 수집·이용 동의가 필요합니다.");
					$('.all_agree').focus();
					return false;
				}					
			}else {
				alert("이메일형식을 제대로 입력해주세요");
				$('#email').focus();
				return false;
			}
		} else {
			alert("회원정보를 제대로 입력해주세요");
			return false;
		}
	});
}