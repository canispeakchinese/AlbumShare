<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title><c:out value="${albumName}" /></title>
<style type="text/css">
@import url("<c:url value="/css/bootstrap.css"/>");
</style>
</head>
<body>
	
<nav class="navbar navbar-default">

  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/AlbumShare/check-login">主页</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/AlbumShare/brower">浏览所有公开图片 <span class="sr-only">(current)</span></a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/AlbumShare/infor"><c:out value="${user.username}" /></a></li>
        <li class="dropdown">
          <a href="/AlbumShare/logout" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">注销<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
	
<div class="row">
	<c:forEach var="photoInfor" items="${photoInfors}">
	  <div class="col-sm-6 col-md-4">
	    <div class="thumbnail">
	      <c:url var="imgPath" value="/img/${userId}/${albumId}/${photoInfor.id}" />
	      <img src="${imgPath}" alt="${photoInfor.description}" /> 
	      <div class="caption">
	        <h3>
	        	<c:out value="${photoInfor.photoName}" />
	        	<c:if test="${photoInfor.isOpen==true}">(已公开)</c:if>
   				<c:if test="${photoInfor.isOpen==false}">(未公开)</c:if>
   			</h3>
	        <h5>描述:<c:out value="${photoInfor.description}" /></h5>
	        <p><c:out value="${photoInfor.comment}" /></p>
	        <a href="/AlbumShare/delete/${photoInfor.id}" >删除</a>
	        <a href="/AlbumShare/photoinfor/${photoInfor.id}" >编辑</a>
	      </div>
	    </div>
	  </div>
	</c:forEach>
	
	<div class="col-sm-6 col-md-4">
	  <div class="thumbnail">
	    <c:url var="imgPath" value="/img/添加新图片" />
	    <a href="/AlbumShare/addpicture">
	   	  <img src="${imgPath}" />
	    </a> 
	  </div>
	</div>
</div>

</body>
</html>
