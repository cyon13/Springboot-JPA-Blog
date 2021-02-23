<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<input type="hidden" id="id" value="${board.id}" />
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.id == principal.user.id}">
	<a href="/board/${board.id}/updateForm" id="btn-update" class="btn btn-warning">수정</a>
	<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br /> <br />
	
	<div>
		작성자: <span><i>${board.user.username}</i></span>
	</div>
	<div>
		<h3>${board.title}</h3>
	</div>
	<hr />
	<div>
		<div>${board.content}</div>
	</div>
	<hr />
</div>

<script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
    </script>
<script src="/js/user/detail.js"></script>
<%@ include file="../layout/footer.jsp"%>




