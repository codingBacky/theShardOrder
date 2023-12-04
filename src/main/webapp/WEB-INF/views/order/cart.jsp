<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Cart</title>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
	  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
	  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
	  <script src="https://kit.fontawesome.com/e73d217c71.js" crossorigin="anonymous"></script>
</head>

<body>
<jsp:include page="../header.jsp"></jsp:include>
    <!-- Page Header Start -->
    
        <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
            <h1 class="font-weight-semi-bold text-uppercase mb-3">ORDER LIST</h1>
            <div class="d-inline-flex">
                <p><a href="">01 장바구니 >&nbsp;</a></p>
                <p>02 주문결제 > 03 주문완료</p>

            </div>
        </div>

    <!-- Page Header End -->


    <!-- Cart Start -->
    <div class="container-fluid">
        <div class="row px-xl-5">
            <div class="col-lg-8 table-responsive">
				<table class="table text-left">
					<thead>
						<tr>
							<th><input type="checkbox" id="selectAllCheckbox" /> 상품명</th>
							<th>수량</th>
							<th>적립</th>
							<th>가격</th>
							<th>배송비</th>
						</tr>
					</thead>

					<tbody class="align-middle">
					<c:if test="${empty itemList}">
					<tr>
						<td>
						    <div class="text-center">
						        <h3 class="font-weight-semi-bold">장바구니에 담긴 상품이 없습니다.</h3>
						    </div>
					    </td>
					</tr>
					</c:if>
					<c:if test="${not empty itemList}">
						<c:forEach var="item" items="${itemList}" varStatus="loop">
							<tr>
								<td><label class="form-check-label"
									for="flexCheckChecked_${loop.index}"> <input
										type="checkbox" class="itemCheckbox"
										id="flexCheckChecked_${loop.index}"> <img
										src="${item.mainImg}" alt="" style="width: 50px;">
										${item.itemName}
								</label></td>
								<td>
									<div class="input-group quantity mx-auto" style="width: 100px;">
										<div class="input-group-btn">
											<button class="btn btn-sm btn-light btn-minus">
												<i class="fa fa-minus"></i>
											</button>
										</div>
										<!-- 추출된 extractCartItemCnts 리스트의 현재 인덱스에 해당하는 값 -->
										<input type="text"
											class="form-control form-control-sm text-center"
											value="${extractCartItemCnts[loop.index]}" />
										<div class="input-group-btn">
											<button class="btn btn-sm btn-light btn-plus">
												<i class="fa fa-plus"></i>
											</button>
										</div>
									</div>
								</td>
								<!-- 추출된 itemRewardPoints 리스트의 현재 인덱스에 해당하는 값 -->
								<td class="align-middle">${itemRewardPoints[loop.index]}</td>
								<td class="align-middle">${item.sale}</td>
								<td class="align-middle">[기본배송] ${deliveryCharge}원 <!-- 상품삭제버튼 -->
									<button class="btn btn-sm btn-light">
										<i class="fa fa-times"></i>
									</button>
								</td>
								
							</tr>
						</c:forEach>
					</c:if>
					</tbody>
					<tfoot class = "align-middle">
						<tr>
							<td>
							<div class="cart-tfoot text-right">
			                    		<span class="cart-price">총 상품금액</span>
			                            <span class="cart-won"> ${totalPrice}</span>
			                </div>
			                </td>
		                </tr>
	                </tfoot>
                </table>
                <input type="hidden" id="email" value="${email}" />
                <input type="hidden" id="cartNum" value="${cartNum}" />
               <c:if test="${not empty itemList}">
				    <!-- itemList가 비어있지 않을 때만 버튼을 보여줌 -->
				    <button type="button" class="btn btn-dark" id="allDeleteButton">전체상품삭제</button>
				    <button type="button" class="btn btn-light">선택상품삭제</button>
				</c:if>
            </div>
            
            <div class="col-lg-4">
                <div class="card border-secondary">
                    <div class="card-header bg-dark border-0">
                        <h5 class="font-weight-semi-bold text-white text-center m-0">예상 결제금액</h5>
                    </div>
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <h6 class="font-weight-medium">총 상품금액</h6>
                            <h6 class="font-weight-medium">${totalPrice}</h6>
                        </div>
                        <div class="d-flex justify-content-between">
                            <h6 class="font-weight-medium">배송비</h6>
                            <h6 class="font-weight-medium">${deliveryCharge}</h6>
                        </div>
                    </div>
                    <div class="card-footer border-secondary bg-transparent">
                        <div class="d-flex justify-content-between">
                            <h5 class="font-weight-bold">총 주문금액</h5>
                            <h5 class="font-weight-bold">${TotalPriceWithShipping}</h5>
                        </div>
                        <button class="btn btn-block btn-dark my-3 py-3">주문하기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Cart End -->



	<jsp:include page="../footer.jsp"></jsp:include>
  



<script>
    // 페이지 로드 시
    document.addEventListener('DOMContentLoaded', function () {
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
    });
    
    $(document).ready(function () {
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
    });
</script>
</body>

</html>