<%@page import="com.shard.domain.ShardMemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<div class="header">
	<div class="topRightNav">
		<ul>
			<%
			ShardMemberVO user = (ShardMemberVO) session.getAttribute("user"); // 유저 정보
			String token = (String) session.getAttribute("kakaoAccessToken"); // 카카오 토큰을 가져오기

			if (user != null) {
			%>
			<li><span
				style="font-size: 14px; color: #333; font-weight: 400;"><%=user.getUserName()%>
					님</span></li>
			<c:choose>
				<c:when test="${not empty token}">
					<li><a
						href="https://kauth.kakao.com/oauth/logout?client_id=4f8fd0ea2b58d54fc209c75db615c0e7&logout_redirect_uri=http://localhost:8181/shard/kakaoLogout">로그아웃</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="/shard/logout">로그아웃</a></li>
				</c:otherwise>
			</c:choose>
			<%
			} else {
			%>
			<li><a href="/shard/login">로그인</a></li>
			<li><a href="/shard/join">회원가입</a></li>
			<%
			}
			%>
			<li><a href="#">주문조회</a></li>
			<li><a href="/shard/qna">Q&A</a></li>
		</ul>
	</div>
	<div class="mainNav">
		<a href="/shard/" style="color: #000; font-family: cursive;"> <img
			src="/resources/logo.png" alt="로고 사진" />
		</a>
		<ul>
			<li><a href="/shard/category/best">BEST</a></li>
			<li><a href="/shard/category/best">NEW</a></li>
			<li class="division_line"><span></span></li>
			<li><a href="/shard/category/2">HoodZipup</a></li>
			<li><a href="/shard/category/11">Mtm</a></li>
			<li><a href="/shard/category/1">Hood</a></li>
			<li><a href="/shard/category/8">Cotton</a></li>
			<li><a href="/shard/category/6">Denim</a></li>
			<li><a href="/shard/category/4">Shirts</a></li>
			<li><a href="/shard/category/acc">Acc</a></li>
		</ul>
	</div>
</div>
