$(function () {
    // 전체 선택을 위한 마스터 체크박스
    const masterCheckbox = document.getElementById('selectAllCheckbox');

    // 각 행의 체크박스들
    const itemCheckboxes = document.querySelectorAll('.itemCheckbox');

    // 페이지 로드 시 마스터 체크박스와 모든 하위 체크박스 선택
    masterCheckbox.checked = true;
    itemCheckboxes.forEach(function (checkbox) {
        checkbox.checked = true;
    });

    // 전체 선택을 위한 마스터 체크박스 상태 변경 시
    masterCheckbox.addEventListener('change', function () {
        // 전체 체크박스들의 상태를 마스터 체크박스에 맞게 설정
        itemCheckboxes.forEach(function (checkbox) {
            checkbox.checked = masterCheckbox.checked;
        });
    });

    // 각 행의 체크박스 상태 변경 시
    itemCheckboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            // 모든 체크박스가 선택되었는지 확인
            const allChecked = Array.from(itemCheckboxes).every(function (checkbox) {
                return checkbox.checked;
            });
            // 마스터 체크박스의 상태를 변경
            masterCheckbox.checked = allChecked;
        });
    });

    $('.plus').click(function(e){
    	e.preventDefault();
        var plusrealSale = parseInt($(this).parent().siblings(".itemRealSale").val());
        console.log("함수 실행");
        var itemCount = $(this).parent().siblings(".itemCount").val();
        itemCount++;
        console.log(itemCount);
        $(this).parent().siblings(".itemCount").val(itemCount);
        var sale = parseInt($(this).parents().siblings(".itemSale").text());
        sale = sale + plusrealSale;
        $(this).parents().siblings(".itemSale").text(sale);
    });

    $('.minus').click(function(e){
    	e.preventDefault();
        var minusrealSale = parseInt($(this).parent().siblings(".itemRealSale").val());
        var itemCount = $(this).parent().siblings(".itemCount").val();
        itemCount--;
        $(this).parent().siblings(".itemCount").val(itemCount);
        var sale = parseInt($(this).parents().siblings(".itemSale").text());
        realSale = parseInt($(this).parents().siblings(".itemSale").text());
        sale = sale - minusrealSale;
        $(this).parents().siblings(".itemSale").text(sale);
    })
});


// 전체상품삭제 버튼 클릭 시
$("#allDeleteButton").click(function () {
    // cartNum 값을 가져오기
    var cartNum = $("#cartNum").val();
    var email = $("#email").val();
    console.log(cartNum);
    // AJAX 호출
    $.ajax({
        type: "POST",
        url: "/order/allDetailCartDelete",
        data: {
            "cartNum": cartNum,
            "email": email
        },
        success: function () {
            // 서버에서의 처리가 성공한 경우
            // 클라이언트 측에서 리다이렉트
            window.location.href = "/order/cartReflash?email=" + encodeURIComponent(email) + "&cartNum=" + encodeURIComponent(cartNum);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 에러 처리
            console.error("AJAX 요청 중 에러 발생:", textStatus, errorThrown);
            alert("에러가 발생했습니다.");
        }
    });
});

//선택한 항목 삭제를 처리하는 함수
var cartNum = $('#cartNum').val();
var email = $("#email").val();
        
function deleteSelectedItems() {
    var selectedItems = [];
    // 모든 체크박스를 순회
    $('.itemCheckbox:checked').each(function () {
       var itemIndex = parseInt($(this).siblings("input[name='itemNum']").val());
        console.log(itemIndex);
        // 체크박스 ID에서 항목 번호를 추출
        selectedItems.push(itemIndex);
        
    });
   console.log(selectedItems);
   
    // 선택한 항목이 있는지 확인
    if (selectedItems.length > 0) {
        
        $.ajax({
            type: 'POST',
            url: '/order/chooseDetailCartDelete',
            contentType: "application/json",
            traditional: true,
            data  :  { "selectedItems": selectedItems},
            success: function(response) {
                // 서버에서의 처리가 성공한 경우
                // 클라이언트 측에서 리다이렉트
                location.href = "/order/cartReflash?email=" + encodeURIComponent(email) + "&cartNum=" + encodeURIComponent(cartNum);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // 에러 처리
                console.error("AJAX 요청 중 에러 발생:", textStatus, errorThrown);
                alert("에러가 발생했습니다.");
            }
        });
    } else {
        alert('삭제할 항목을 선택하세요.');
    }
}


