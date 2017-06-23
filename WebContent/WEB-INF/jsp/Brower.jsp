<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>浏览所有公开图片</title>
<style type="text/css">
@import url("<c:url value="/css/bootstrap.css"/>");
</style>
</head>
<body>
	
<a href="/AlbumShare/login" >登录</a>
<br />
<br />
	
<div class="row">
	<c:forEach var="photo" items="${photos}">
	  <div class="col-sm-6 col-md-4">
	    <div class="thumbnail">
	      <c:url var="imgPath" value="/img/${photo.userId}/${photo.albumId}/${photo.photoId}" />
	      <img src="${imgPath}" alt="${photo.description}" /> 
	      <div class="caption">
	        <h3><c:out value="${photo.photoName}" /></h3>
	        <h5>描述:<c:out value="${photo.description}" /></h5>
	        <p><c:out value="${photo.comment}" /></p>
	      </div>
	    </div>
	  </div>
	</c:forEach>
</div>

</body>
</html>
