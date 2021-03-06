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
<script type="text/javascript">
	
</script>
</head>
<body>
	<div id="container">
		<c:if test="${not empty authUser }">
			<c:import url="/WEB-INF/views/includes/blog-header.jsp">
				<c:param name="blogTitle" value="${authUser.blogTitle }" />
			</c:import>
		</c:if>
		<c:if test="${empty authUser }">
			<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		</c:if>

		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/admin-menu.jsp" />

				<table class="admin-cat">
					<tr>
						<th>번호</th>
						<th>카테고리명</th>
						<th>포스트 수</th>
						<th>설명</th>
						<th>삭제</th>
					</tr>
					<c:forEach items="${list }" var="category" varStatus="status">
						<tr>
							<td class='idx'>${status.index + 1}</td>
							<td>${category.name }</td>
							<td class='post-count'>${category.postCount }</td>
							<td>${category.desc }</td>
							<td><a class='delete-category' data-no='${category.no }'
								href=''> <img
									src="${pageContext.request.contextPath}/assets/images/delete.jpg">
							</a></td>
						</tr>
					</c:forEach>
				</table>

				<h4 class="n-c">새로운 카테고리 추가</h4>
				<table id="admin-cat-add">
					<tr>
						<td class="t">카테고리명</td>
						<td><input type="text" id='category-name' name="name"></td>
					</tr>
					<tr>
						<td class="t">설명</td>
						<td><input type="text" id='category-desc' name="desc"></td>
					</tr>
					<tr>
						<td class="s">&nbsp;</td>
						<td><input id='btn-addcategory' type="submit" value="카테고리 추가"></td>
					</tr>
				</table>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp" />
	</div>
	<script type="text/javascript">
		
		$(function(){
			$('.admin-cat').click(function(e){
				let aEl = $(e.target).parent()
				if(!aEl.hasClass('delete-category'))
					return;
			
				e.preventDefault()
				let no = aEl.data('no')
				let tr = aEl.parent().parent();
				
				console.log($('.post-count', tr).text())
				if($('.admin-cat .idx').length < 2){
					alert('카테고리는 1개 이상이어야 합니다.')
					return;
				}
				if($('.post-count', tr).text() != 0){
					if(!confirm('카테고리에 등록된 포스트를 전부 삭제하시겠습니까'))
						return
					
				}
				
				$.ajax({
					url : `${pageContext.request.contextPath}/api/blog/${authUser.id}/category/` + no,
					type : 'DELETE',			
					dataType : 'json',
					success: function(response){
						if(response.result == 'fail'){
							alert('실패')
							return;
						}
						
						for(let idxTd of $('.idx',tr.nextAll())) 
							$(idxTd).text(parseInt($(idxTd).text()) - 1)

						tr.remove()						
					},
					error: function(e){
						console.log(e)
						alert('실패')
					}
				})
			})
			
			$('#btn-addcategory').click(function(){
				$.ajax({
					url : `${pageContext.request.contextPath}/api/blog/${authUser.id}/category`,
					type : 'POST',
					data : JSON.stringify({
						name: $('#category-name').val(),
						desc: $('#category-desc').val()
					}),
					contentType: 'application/json',
					dataType : 'json',
					success: function(response){
						if(response.result == 'fail'){
							alert('실패')
							return;
						}
						
						var lastIdx = parseInt($('td',$('.admin-cat tr').last()).first().text())
						var html = `<tr>
									<td class='idx'>` + (lastIdx + 1) + `</td>
									<td>` + response.data.name + `</td>
									<td class='post-count'>` + response.data.postCount + `</td>
									<td>` + response.data.desc + `</td>
									<td>
										<a class='delete-category' href='' data-no='` + response.data.no + `'>
										<img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></td>
										</a>
									</tr>`
						
						$('.admin-cat').append(html) 
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