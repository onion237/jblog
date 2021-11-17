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

				<form id='write-form' action="" method="post">
					<table class="admin-cat-write">
						<tr>
							<td class="t">제목</td>
							<td><input id='title' type="text" size="60" name="title"> 
							<select id='category' name="category">
								<c:forEach items="${list }" var="category" varStatus="status">
									<option value="${category.no }">${category.name }</option>								
								</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td class="t">내용</td>
							<td><textarea id='contents' name="contents"></textarea></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td class="s"><input type="submit" value="포스트하기"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
		
	</div>
	<script type="text/javascript">
	$(function(){
		$('#write-form').submit(function(e){
			e.preventDefault();
			$.ajax({
				url : `${pageContext.request.contextPath}/api/blog/${authUser.id}/posts`,
				type : 'post',
				contentType : 'application/json',
				dataType : 'json',
				data : JSON.stringify({
					categoryNo : $('#category').val(),
					title : $('#title').val(),
					contents : $('#contents').val()
				}),
				success : function(response){
					if(response.result == 'success'){
						alert("작성 완료")
						$('#title').val('');
						$('#contents').val('');
						return;
					}else{
						alert('작성 실패')
					}
				}
			})
		})
	})
	</script>
</body>
</html>