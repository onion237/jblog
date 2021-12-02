<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>​
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>​
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<ul class="admin-menu">
	<li id='menu-basic'><a
		href="${pageContext.request.contextPath}/${authUser.id}/admin">기본설정</a></li>
	<li id='menu-category'><a
		href="${pageContext.request.contextPath}/${authUser.id}/admin/category">카테고리</a></li>
	<li id='menu-write'><a
		href="${pageContext.request.contextPath}/${authUser.id}/admin/write">글작성</a></li>
</ul>
<script>
	window.addEventListener('load', function(){
		let menu = location.href.substring(location.href.lastIndexOf('/admin') + '/admin/'.length) || 'basic';
		console.log(menu)
		
		var selectedMenu = document.getElementById('menu-' + menu);
		selectedMenu.innerHTML = selectedMenu.innerText;
		selectedMenu.classList.add('selected')
	})
</script>