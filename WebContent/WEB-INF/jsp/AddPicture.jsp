<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>主页</title>
<style type="text/css">
@import url("<c:url value="/css/bootstrap.css"/>");
</style>
<style type="text/css">@import url("<c:url value="/css/main.css"/>");</style>
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

<h3 align="center"><c:out value="${error}" /></h3>

<div class="container-fluid">			
	<div class="row">
		<div class="col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2 main">
			<h2 class="page-header">添加新图片</h2>
			
			<form:form class="form-horizontal" commandName="uploadedPhoto"
				action="/AlbumShare/upload" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="photoName" class="col-sm-2 control-label">图片名称（默认为文件名）</label>
					<div class="col-sm-8">
						<form:input class="form-control" id="photoName" path="photoName" />
					</div>
				</div>
				
				<p class="errorLine">
           			<form:errors path="photoName" cssClass="error"/>
       			</p>

				<div class="form-group">
					<label for="description" class="col-sm-2 control-label">描述</label>
					<div class="col-sm-8">
						<form:input class="form-control" id="description" path="description" />
					</div>
				</div>
				
				<p class="errorLine">
           			<form:errors path="description" cssClass="error"/>
       			</p>
				
				<div class="form-group">
					<label for="image" class="col-sm-2 control-label">添加图片</label>
					<div class="col-sm-8">
						<form:input type="file" class="form-control" id="file" path="file" />
					</div>
				</div>
				
				<p class="errorLine">
           			<form:errors path="file" cssClass="error"/>
       			</p>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input class="btn btn-danger" id="reset" type="reset"
							value="重置"> <input class="btn btn-primary"
							id="submit" type="submit" value="添加">
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

</body>
</html>
