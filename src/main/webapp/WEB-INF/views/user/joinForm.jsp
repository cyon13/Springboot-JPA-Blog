<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<label for="username">Username</label> <input type="text" class="form-control" placeholder="Enter username" id="username" required>
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input type="password" class="form-control" placeholder="Enter password" id="password" required>
		</div>
		<div class="form-group">
			<label for="email">Email address</label> <input type="email" class="form-control" placeholder="Enter email" id="email" required>
		</div>
	</form>
		<button id="btn-save" class="btn btn-primary">Submit</button>
</div>
<script src="/js/user/join.js"></script>
<%@ include file="../layout/footer.jsp"%>




