$(function () {
	// 회원가입 form태그에 있는 input을 focus했을 때 css의 opcity가 0이었던걸 0.1초만에
	// 1로 바꾸고 focus가 out되면 다시 0.3초만에 css의 opacity가 0이 되는 제이쿼리 구문
	$("#joinForm input").focus(function () {
		$(this).animate({ opacity: 1 }, 100);
	}).focusout(function () {
		if ($(this).val().trim() === '') {
			$(this).animate({ opacity: 0 }, 300);
		}
	});

	// "전체 선택" 체크박스의 상태가 변경되었을 때
	$('#all_check').change(function () {
		// "전체 선택" 체크박스의 상태에 따라 다른 체크박스 상태 변경
		var isChecked = $(this).is(':checked');
		$('#playmall, #userinfo').prop('checked', isChecked);
	});

	// 개별 체크박스의 상태가 변경되었을 때
	$('#playmall, #userinfo').change(function () {
		// 개별 체크박스 상태에 따라 "전체 선택" 체크박스 상태 변경
		var playmallChecked = $('#playmall').is(':checked');
		var userinfoChecked = $('#userinfo').is(':checked');
		$('#all_check').prop('checked', playmallChecked && userinfoChecked);
	});
	// "전체 선택" 체크박스의 상태가 변경되었을 때
	$('.all_agree').change(function () {
		// "전체 선택" 체크박스의 상태 가져오기
		var isChecked = $(this).is(':checked');
		// 모든 "전체 선택" 체크박스의 상태 변경
		$('.all_agree').prop('checked', isChecked);
	});
});

// 주소가 정상적으로 입력되었는지 체크
function addressCheck() {
	var zipCode = $('#postcode').val();
	var userAddr = $("#roadAddress").val() + $("#jibunAddress").val() + $("#extraAddress").val();
	var detailAddr = $("#detailAddress").val();
	var check5 = false;

	if (zipCode == "" && userAddr == "" && detailAddr == "") {
		return false;
	} else {
		check5 = true;
	}

	return check5;
}

function formSubmit() {
	var check3 = $('.all_agree').is(":checked"); // 개인정보 동의 체크
	var check5 = addressCheck();

	// 휴대폰 인증
	// 생일 인증
	// 약관 인증까지

	// 모든 인증이 다 들어가야 한다.

	if (check3) { // 개인정보 동의 체크
		if (check5) { // 회원 주소 체크
			$('#joinForm').submit();
		} else {
			alert("회원님의 주소는 필수입력입니다.");
		}
	} else {
		alert("개인정보 수집·이용 동의가 필요합니다.");
		$('.all_agree').focus();
		return false;
	}
}