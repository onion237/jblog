<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
</head>
<body>
	<div class="center-content">
		<!--  
		<h1 class="logo">JBlog</h1>
	-->
		<c:import url="/WEB-INF/views/includes/main-header.jsp" />
		<form class="join-form" id="join-form" method="post"
			action="${pageContext.request.contextPath }/user/join">
			<label class="block-label" for="name">이름</label> <input id="name"
				name="name" type="text" value="${userVo.name}">
			<p style="text-align: left; padding-left: 0; color: #f00">
				<spring:hasBindErrors name="userVo">
					<c:if test="${errors.hasFieldErrors('name') }">
						${errors.getFieldError('password').defaultMessage }
					</c:if>
				</spring:hasBindErrors>
			</p>

			<label class="block-label" for="blog-id">아이디</label> <input
				id="blog-id" name="id" type="text" value="${userVo.id}"> <input
				id="btn-checkemail" type="button" value="id 중복체크"> <img
				id="img-checkemail" style="display: none;"
				src="${pageContext.request.contextPath}/assets/images/check.png">
			<p style="text-align: left; padding-left: 0; color: #f00">
				<spring:hasBindErrors name="userVo">
					<c:if test="${errors.hasFieldErrors('id') }">
						${errors.getFieldError('password').defaultMessage }
					</c:if>
				</spring:hasBindErrors>
			</p>

			<label class="block-label" for="password">패스워드</label> <input
				id="password" name="password" type="password" />
			<p style="text-align: left; padding-left: 0; color: #f00">
				<spring:hasBindErrors name="userVo">
					<c:if test="${errors.hasFieldErrors('password') }">
						${errors.getFieldError('password').defaultMessage }
					</c:if>
				</spring:hasBindErrors>
			</p>

			<input id='btn-regist' type="submit" value="가입하기">
		</form>
	</div>
	<script type="text/javascript">
	$(function() {
		$('#blog-id').on("change paste input", () => {
			$('#btn-regist').attr('disabled', true)
			$("#btn-checkemail").show()
			$("#img-checkemail").hide()
		})
		
		$('#btn-checkemail').click(() => {	
			let id = $('#blog-id').val();
			if(id == ''){
				return;
			}
			
			$.ajax({
				url: `${pageContext.request.contextPath}/api/user/checkid`,
				type : 'POST',
				data : {
					'id' : id
				},
				dataType : 'json',
				success: function(response){
					console.log(response)
					if(response.result == 'success'){
						alert('사용할 수 있는 이메일입니다.')
						$('#btn-regist').attr('disabled', false)
						$("#btn-checkemail").hide()
						$("#img-checkemail").show()
					}else{
						alert('사용할 수 없는 이메일입니다.')
						$('#btn-regist').attr('disabled', true)
						$("#blog-id").val('');
						$("#blog-id").focus();
					}
				},
				error: function(xhr,status,error){
					console.log(xhr)
					console.log(status)
					console.log(error)
				}
			})
			
		});
	})
	</script>
</body>
</html>
