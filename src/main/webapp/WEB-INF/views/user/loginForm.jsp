<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username</label> <input name="username" type="text" class="form-control" placeholder="Enter username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input name="password" type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=44d303b07ae72f0f38911251fa7d55bb&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code"><img height="38px" src="/images/kakao_login_button.png"/></a>
	</form>
</div>


<%@ include file="../layout/footer.jsp"%>




