<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:if test="${not empty authUser }">
			<c:import url="/WEB-INF/views/includes/blog-header.jsp">
				<c:param name="blogTitle" value="${authUser.blogTitle }"/>
			</c:import>
		</c:if>
		<c:if test="${empty authUser }">
			<c:import url="/WEB-INF/views/includes/blog-header.jsp"/>
		</c:if>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/admin-menu.jsp" />

				<form id='form-blog-basic' action="">
					<input type='hidden' name='no' value='${blog.no }'>
					<table class="admin-config">
						<tr>
							<td class="t">블로그 제목</td>
							<td><input id='title' type="text" size="40" name="title"
								value='${blog.title }'></td>
						</tr>
						<tr>
							<td class="t">로고이미지</td>
							<td><img id='logo-img'
								src="${pageContext.request.contextPath}${blog.logo}"></td>
						</tr>
						<tr>
							<td class="t">&nbsp;</td>
							<td><input id='logo-file' type="file" name="file"></td>
						</tr>
						<tr>
							<td class="t">&nbsp;</td>
							<td class="s"><input type="submit" value="기본설정 변경"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#form-blog-basic').submit(function(e){
				e.preventDefault();
				window.form = new FormData($('#form-blog-basic')[0])
				
				$.ajax({
					url : `${pageContext.request.contextPath}/api/blog/${authUser.id}`,
					type : 'POST',
					data : form,
					processData : false,
					contentType : false,
					dataType : 'json',
					success: function(response){
						console.dir(response)
						$('#title').val(response.data.title)
						$('#logo-img').attr('src', '${pageContext.request.contextPath}' + response.data.logo)
						
					},
					error: function(e){
						console.log(e)
						alert('실패')
					}
				})
			})
		})
	</script>
</body>
</html>