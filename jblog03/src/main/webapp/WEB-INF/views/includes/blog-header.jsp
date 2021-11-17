<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>​
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>​
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<div id="header">
	
	<c:if test="${not empty param.blogTitle }">
		<h1>${param.blogTitle }</h1>	
	</c:if>
	<c:if test="${empty param.blogTitle }">
		<h1>${blog.title }</h1>	
	</c:if>
	<ul>
		<c:if test="${empty authUser}">
			<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
		</c:if>
		<c:if test="${not empty authUser }">
			<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
			<c:if test="${authUser.no == blog.no }">
				<li><a
					href="${pageContext.request.contextPath}/${authUser.id}/admin">블로그
						관리</a></li>
			</c:if>
		</c:if>
	</ul>
</div>